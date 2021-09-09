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
        JSONObject result = new JSONObject();
        Archive archive = archiveMapper.retrieveArchiveFromArchiveNo(archive_no, user_no);
        ArrayList<String> tagList = tagMapper.retrieveArchiveTagByArchiveNo(archive.getNo());
        archive.setTag_list(tagList);
        result.put("archive", archive);
        return result;
    }
}
