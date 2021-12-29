package com.moaka.service;

import com.moaka.dto.Chat;
import com.moaka.dto.User;
import com.moaka.mapper.ChatMapper;
import com.moaka.mapper.TestMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatService {
    @Autowired
    ChatMapper chatMapper;

    public ArrayList<Chat> retrieveChatByRoomNo(int room_no, int user_no) {
        return chatMapper.retrieveChatByRoomNo(room_no, user_no);
    }

    @Async
    public void insertChat(Chat chat) {
        chatMapper.insertChat(chat);
    }
}
