package com.moaka.service;

import com.moaka.common.cdn.CdnService;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.dto.Archive;
import com.moaka.dto.Tag;
import com.moaka.mapper.ArchiveMapper;
import com.moaka.mapper.TagMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class ArchiveService {
    @Autowired
    ArchiveMapper archiveMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    CdnService cdnService;

    public void insertArchive(Archive params) {
        String today = getToday();

        // CDN 썸네일 생성
        String thumbnailUrl = cdnService.FileUpload("archive/thumbnail", params.getThumbnailFile());
        params.setRegdate(today);
        params.setThumbnail(thumbnailUrl);

        // DB 아카이브 정보 생성
        archiveMapper.insertArchive(params);

        // DB 아카이브 그룹 생성
        archiveMapper.insertArchiveOfGroup(params.getUser_no(), params.getNo(), today);
        for (int i = 0; i < params.getGroup_no_list().size(); i++) {
            archiveMapper.insertArchiveOfGroup(params.getGroup_no_list().get(i), params.getNo(), today);
        }

        // DB 아카이브 태그 생성
        for (int i = 0; i < params.getTag_list().size(); i++) {
            Tag tag = new Tag();
            tag.setTag(params.getTag_list().get(i));
            tag.setRegdate(today);
            tag.setArchive_no(params.getNo());

            tagMapper.insertArchiveTag(tag);
        }
    }

    public JSONObject updateArchive(Archive params) throws IOException {
        JSONObject result = new JSONObject();

        if (params.getThumbnailFile() != null) {
            cdnService.FileDelete(params.getThumbnail());
            String thumbnailUrl = cdnService.FileUpload("archive/thumbnail", params.getThumbnailFile());
            params.setThumbnail(thumbnailUrl);
        }

        // 아카이브 정보 업데이트
        archiveMapper.updateArchive(params);

        // 아카이브 그룹 정보 업데이트
        String today = getToday();
        archiveMapper.deleteArchiveOfGroupByArchiveNo(params.getNo());
        for (int i = 0; i < params.getGroup_no_list().size(); i++) {
            archiveMapper.insertArchiveOfGroup(params.getGroup_no_list().get(i), params.getNo(), today);
        }

        // 아카이브 태그 정보 업데이트
        tagMapper.deleteArchiveTagByArchiveNo(params.getNo());
        for (int i = 0; i < params.getTag_list().size(); i++) {
            Tag tag = new Tag();
            tag.setTag(params.getTag_list().get(i));
            tag.setRegdate(today);
            tag.setArchive_no(params.getNo());

            tagMapper.insertArchiveTag(tag);
        }

        result.put("thumbnail", params.getThumbnail());
        result.put("isSuccess", true);

        return result;
    }

    public JSONObject retrieveArchiveOfGroupByUserNo(int user_no) {
        JSONObject result = new JSONObject();
        ArrayList<Archive> archiveList = archiveMapper.retrieveArchiveOfGroupByUserNo(user_no);
        for (int i = 0; i < archiveList.size(); i++) {
            ArrayList<String> tagList = tagMapper.retrieveArchiveTagByArchiveNo(archiveList.get(i).getNo());
            archiveList.get(i).setTag_list(tagList);
        }
        result.put("archive_list", archiveList);

        return result;
    }

    public JSONObject retrieveArchiveOfBookmarkByUserNo(int user_no) {
        JSONObject result = new JSONObject();
        ArrayList<Archive> archiveList = archiveMapper.retrieveArchiveOfBookmarkByUserNo(user_no);
        for (int i = 0; i < archiveList.size(); i++) {
            ArrayList<String> tagList = tagMapper.retrieveArchiveTagByArchiveNo(archiveList.get(i).getNo());
            archiveList.get(i).setTag_list(tagList);
        }
        result.put("archive_list", archiveList);

        return result;
    }

    public JSONObject retrieveArchiveOfTop() {
        JSONObject result = new JSONObject();
        ArrayList<Archive> archiveList = archiveMapper.retrieveArchiveOfTop();
        for (int i = 0; i < archiveList.size(); i++) {
            ArrayList<String> tagList = tagMapper.retrieveArchiveTagByArchiveNo(archiveList.get(i).getNo());
            archiveList.get(i).setTag_list(tagList);
        }
        result.put("archive_list", archiveList);

        return result;
    }

    public JSONObject retrieveArchiveFromArchiveNo(int archive_no, int user_no) {
        JSONObject archiveObj = new JSONObject();
        JSONObject result = new JSONObject();
        Archive archive = archiveMapper.retrieveArchiveFromArchiveNo(archive_no, user_no);
        ArrayList<String> tagList = tagMapper.retrieveArchiveTagByArchiveNo(archive.getNo());
        archive.setTag_list(tagList);

        archiveObj.put("no", archive.getNo());
        archiveObj.put("user_no", archive.getUser_no());
        archiveObj.put("title", archive.getTitle());
        archiveObj.put("description", archive.getDescription());
        archiveObj.put("thumbnail", archive.getThumbnail());
        archiveObj.put("creator_name", archive.getCreator_name());
        archiveObj.put("privacy_type", archive.getPrivacy_type());
        archiveObj.put("regdate", archive.getRegdate());
        archiveObj.put("tag_list", archive.getTag_list());
        archiveObj.put("bookmark_no", archive.getBookmark_no());
        archiveObj.put("like_no", archive.getLike_no());
        archiveObj.put("category", archive.getCategory());

        result.put("archive", archiveObj);
        return result;
    }

    public JSONObject retrieveArchiveOfCategory(List<String> categoryList) {
        JSONObject result = new JSONObject();
        ArrayList<Archive> archiveList = archiveMapper.retrieveArchiveOfCategory(categoryList);

        for (int i = 0; i < archiveList.size(); i++) {
            ArrayList<String> tagList = tagMapper.retrieveArchiveTagByArchiveNo(archiveList.get(i).getNo());
            archiveList.get(i).setTag_list(tagList);
        }

        result.put("isSuccess", true);
        result.put("archive_list", archiveList);

        return result;
    }

    public JSONObject deleteArchiveFromArchiveNo(int archive_no, int user_no) {
        JSONObject result = new JSONObject();
        if (archiveMapper.isAuthorityOfDeleteArchive(archive_no, user_no)) {
            // 아카이브를 삭제할 권한이 있음
            archiveMapper.deleteArchiveFromArchiveNo(archive_no);
            result.put("isSuccess", true);
        } else {
            // 아카이브를 삭제할 권한이 없음
            result.put("isSuccess", false);
        }
        return result;
    }

    public JSONObject retrieveArchiveBySearch(String param, int user_no) {
        JSONObject result = new JSONObject();
        ArrayList<Archive> archiveList = archiveMapper.retrieveArchiveBySearch(param, user_no);
        for (int i = 0; i < archiveList.size(); i++) {
            ArrayList<String> tagList = tagMapper.retrieveArchiveTagByArchiveNo(archiveList.get(i).getNo());
            archiveList.get(i).setTag_list(tagList);
        }
        result.put("isSuccess", true);
        result.put("archive_list", archiveList);

        return result;
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
