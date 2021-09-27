package com.moaka.service;

import com.moaka.dto.Archive;
import com.moaka.dto.Chunk;
import com.moaka.dto.Section;
import com.moaka.dto.User;
import com.moaka.mapper.ArchiveMapper;
import com.moaka.mapper.AuthMapper;
import com.moaka.mapper.BookmarkMapper;
import com.moaka.mapper.SectionMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Service
@Transactional
public class UserService {
    @Autowired
    ArchiveMapper archiveMapper;
    @Autowired
    SectionMapper sectionMapper;
    @Autowired
    AuthMapper authMapper;

    public JSONArray retrieveDirectory(int user_no) throws Exception {
        JSONArray directoryList = new JSONArray();
        ArrayList<Archive> archiveList = archiveMapper.retrieveArchiveByUserNo(user_no);
        for(int i = 0; i < archiveList.size(); i++) {
            JSONObject directoryInfo = new JSONObject();
            Archive archive = archiveList.get(i);
            ArrayList<Section> sectionList = sectionMapper.retrieveSectionByArchiveNo(archive.getNo());

            if (sectionList.size() != 0) {
                directoryInfo.put("archive_no", archive.getNo());
                directoryInfo.put("archive_title", archive.getTitle());
                JSONArray sectionArray = new JSONArray();
                for (int j = 0; j < sectionList.size(); j++) {
                    JSONObject sectionInfo = new JSONObject();
                    sectionInfo.put("no", sectionList.get(j).getNo());
                    sectionInfo.put("title", sectionList.get(j).getTitle());
                    sectionArray.put(sectionInfo);
                }
                directoryInfo.put("section_list", sectionArray);
                directoryList.put(directoryInfo);
            }
        }
        return directoryList;
    }

    public JSONObject retrieveUserListById(String id) {
        JSONObject result = new JSONObject();

        ArrayList<User> userList = authMapper.retrieveUserListById(id);
        if(userList.size() != 0) {
            // 아이디를 찾음
            JSONArray userInfoList = new JSONArray();
            for(int i = 0; i < userList.size(); i++) {
                JSONObject userInfo = new JSONObject();
                userInfo.put("no", userList.get(i).getNo());
                userInfo.put("id", userList.get(i).getId());
                userInfo.put("name", userList.get(i).getName());
                userInfo.put("profile", userList.get(i).getProfile());
                userInfoList.put(userInfo);
            }

            result.put("user_list", userInfoList);
            result.put("isSuccess", true);
        } else {
            // 아이디를 찾지 못함
            result.put("isSuccess", false);
        }

        return result;
    }
}
