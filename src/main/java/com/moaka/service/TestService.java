package com.moaka.service;

import com.moaka.dto.User;
import com.moaka.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TestService {
    @Autowired
    TestMapper testMapper;

    public ArrayList<User> getUser() {
        ArrayList<User> userList = testMapper.getUser();
        return userList;
    }

    public void getUserCount() {
        System.out.println(testMapper.getUserCount());
    }
}
