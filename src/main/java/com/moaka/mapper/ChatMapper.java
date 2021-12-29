package com.moaka.mapper;

import com.moaka.dto.Chat;
import com.moaka.dto.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface ChatMapper {
    ArrayList<Chat> retrieveChatByRoomNo(int room_no);
    void insertChat(Chat chat);
}
