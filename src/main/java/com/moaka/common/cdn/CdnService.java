package com.moaka.common.cdn;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

@Service
public class CdnService {
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private AmazonS3 s3Client;

    @PostConstruct
    public void setS3Client() {
        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

            s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.AP_NORTHEAST_2)
                    .build();

            System.out.println("생성 버킷 이름: " + bucketName);
            if (!s3Client.doesBucketExistV2(bucketName)) {
                s3Client.createBucket(bucketName);

                // Verify that the bucket was created by retrieving it and checking its location.
                String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName));
                System.out.println("Bucket location: " + bucketLocation);
            } else {
                System.out.println("amazonS3 Bucket Exist!!");
            }
        } catch (AmazonClientException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it and returned an error response.
            e.printStackTrace();
        }
    }

    //파일을 업로드 후 경로를 리턴
    public String FileUpload(String filePath, MultipartFile mf) {
        long time = System.currentTimeMillis();
        String originalFilename = mf.getOriginalFilename();
        String saveFileName = String.format("%d_%s", time, originalFilename.replaceAll(" ", ""));

        // 파일 생성
        File uploadFile = null;
        try {
            Optional<File> uploadFileOpt = convertMultipartFileToFile(mf);
            if (uploadFileOpt.isEmpty()) {
                throw new InternalServiceException(ErrorCode.FILE_NOT_CHANGE.getErrorCode(), ErrorCode.FILE_NOT_CHANGE.getErrorMessage());
            }
            uploadFile = uploadFileOpt.get();

            String fileName = filePath + "/" + saveFileName;
            // 파일 업로드
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

            return "https://moaka-s3.s3.ap-northeast-2.amazonaws.com/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServiceException(ErrorCode.AWS_CDN_NOT_WORK.getErrorCode(), ErrorCode.AWS_CDN_NOT_WORK.getErrorMessage());
        } finally {
            // 파일 삭제
            if (uploadFile != null) {
                uploadFile.delete();
            }
        }

//        File file = multipartToFile(multipartFile);
//        String awsCdnFilePath = path + genSaveFileName() + multipartFile.getOriginalFilename();
//        s3Client.putObject(bucketName, awsCdnFilePath, file);
//        file.delete();
//        return awsCdnFilePath;
    }

    //jsonArray 형태로 파일 경로 리턴
    public JSONArray FileArrayUpload(String path, ArrayList<MultipartFile> multipartFileArrayList) throws IOException {
        JSONArray pathArrayList = new JSONArray();
        for (int i = 0; i < multipartFileArrayList.size(); i++) {
            if (multipartFileArrayList.get(i) != null) {
                String awsCdnFilePath = path + genSaveFileName() + multipartFileArrayList.get(i).getOriginalFilename();
                pathArrayList.put(awsCdnFilePath);
                File file = multipartToFile(multipartFileArrayList.get(i));
                s3Client.putObject(bucketName, awsCdnFilePath, file);
                file.delete();
            }
        }
        return pathArrayList;
    }

    public void FileDelete(String awsCdnFilePath) throws IOException {
        s3Client.deleteObject(bucketName, awsCdnFilePath); //#1 - 버킷에 있는 파일 하나 삭제
    }

    public void FileArrayDelete(String awsCdnFileArrayPath[]) throws IOException {
        DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(bucketName).withKeys(awsCdnFileArrayPath);
        s3Client.deleteObjects(delObjReq); //#2 - 버킷에 있는 여러 파일 삭제
    }

    // MultipartFile 을 file로 변형 후 로컬로 저장, 파일객체를 리턴
    public Optional<File> convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        String fileName = multipart.getOriginalFilename().replaceAll(" ", "");
        fileName = fileName.replaceAll("\\p{Z}", "");
        File convFile = new File(fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    // 현재 시간을 기준으로 이름 생성
    private String genSaveFileName() {
        String fileName = "";

        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += "_";
        fileName += calendar.get(Calendar.MONTH);
        fileName += "_";
        fileName += calendar.get(Calendar.DATE);
        fileName += "_";
        fileName += calendar.get(Calendar.HOUR);
        fileName += "_";
        fileName += calendar.get(Calendar.MINUTE);
        fileName += "_";
        fileName += calendar.get(Calendar.SECOND);
        fileName += "/";

        return fileName;
    }
}
