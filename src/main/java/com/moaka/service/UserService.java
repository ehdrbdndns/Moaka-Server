package com.moaka.service;

import com.moaka.dto.Archive;
import com.moaka.dto.Chunk;
import com.moaka.dto.Section;
import com.moaka.dto.User;
import com.moaka.mapper.*;
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
    TagMapper tagMapper;
    @Autowired
    UserMapper userMapper;

    public JSONArray retrieveDirectory(int user_no) throws Exception {
        JSONArray directoryList = new JSONArray();
        ArrayList<Archive> archiveList = archiveMapper.retrieveArchiveByUserNo(user_no);
        for(int i = 0; i < archiveList.size(); i++) {
            JSONObject directoryInfo = new JSONObject();
            Archive archive = archiveList.get(i);
            ArrayList<Section> sectionList = sectionMapper.retrieveSectionByArchiveNo(archive.getNo());

            directoryInfo.put("title", archive.getTitle());
            directoryInfo.put("list", sectionList);
            directoryList.put(directoryInfo);
        }
        return directoryList;
    }

    public JSONObject retrieveUserListById(String id) {
        JSONObject result = new JSONObject();

        ArrayList<User> userList = userMapper.retrieveUserListById(id);
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

    public JSONObject retrieveUserListByName(String name) {
        JSONObject result = new JSONObject();

        User user = userMapper.retrieveUserByName(name);
        if(user != null) {
            result.put("user", user);
            result.put("isSuccess", true);
        } else {
            result.put("isSuccess", false);
        }

        return result;
    }

    public JSONObject retrieveGroupUserOfArchiveByArchiveNo(int archive_no) {
        JSONObject result = new JSONObject();
        ArrayList<User> userList = userMapper.retrieveGroupUserOfArchiveByArchiveNo(archive_no);
        result.put("isSuccess", true);
        result.put("user_list", userList);
        return result;
    }
}
