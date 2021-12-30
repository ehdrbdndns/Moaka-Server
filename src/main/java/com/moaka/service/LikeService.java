package com.moaka.service;

import com.moaka.dto.Chat;
import com.moaka.dto.Like;
import com.moaka.mapper.LikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
@Transactional
public class LikeService {
    @Autowired
    LikeMapper likeMapper;

    public int insertLikeOfChunk(Like params) {
        params.setRegdate(getToday());
        likeMapper.insertLikeOfChunk(params);
        return params.getNo();
    }

    public int insertLikeOfArchive(Like params) {
        params.setRegdate(getToday());
        likeMapper.insertLikeOfArchive(params);
        return params.getNo();
    }

    public void deleteArchiveLike(int no) {
        likeMapper.deleteArchiveLike(no);
    }

    public void deleteChunkLike(int no) {
        likeMapper.deleteChunkLike(no);
    }

    public void insertChatLike(Chat chat) {
        likeMapper.insertChatLike(chat);
    }

    @Async
    public void deleteChatLike(int user_no) {
        likeMapper.deleteChatLike(user_no);
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
