package com.moaka.service;

import com.moaka.dto.Archive;
import com.moaka.mapper.ArchiveMapper;
import com.moaka.mapper.TagMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class ArchiveService {
    @Autowired
    ArchiveMapper archiveMapper;
    @Autowired
    TagMapper tagMapper;

    public JSONObject retrieveArchiveFromGroup(int user_no) {
        JSONObject result = new JSONObject();
        ArrayList<Archive> archiveList = archiveMapper.retrieveArchiveFromGroup(user_no);
        for(int i = 0; i < archiveList.size(); i++) {
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

        result.put("archive", archiveObj);
        return result;
    }

    public JSONObject deleteArchiveFromArchiveNo(int archive_no, int user_no) {
        JSONObject result = new JSONObject();
        if(archiveMapper.isAuthorityOfDeleteArchive(archive_no, user_no)) {
            // 아카이브를 삭제할 권한이 있음
            archiveMapper.deleteArchiveFromArchiveNo(archive_no);
            result.put("isSuccess", true);
        } else {
            // 아카이브를 삭제할 권한이 없음
            result.put("isSuccess", false);
        }
        return result;
    }
}
