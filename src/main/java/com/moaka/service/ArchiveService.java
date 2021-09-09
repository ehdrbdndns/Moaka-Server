package com.moaka.service;

import com.moaka.common.config.security.JwtTokenProvider;
import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.common.jwt.EncryptionService;
import com.moaka.dto.Archive;
import com.moaka.dto.User;
import com.moaka.mapper.ArchiveMapper;
import com.moaka.mapper.AuthMapper;
import com.moaka.mapper.SectionMapper;
import com.moaka.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;

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
}
