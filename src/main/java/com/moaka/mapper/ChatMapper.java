package com.moaka.mapper;

import com.moaka.dto.Chat;
import com.moaka.dto.User;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface ChatMapper {
    ArrayList<Chat> retrieveChatByRoomNo(@Param("room_no") int room_no, @Param("user_no") int user_no);
    void insertChat(Chat chat);
}
