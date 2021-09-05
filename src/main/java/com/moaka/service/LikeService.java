package com.moaka.service;

import com.moaka.dto.Bookmark;
import com.moaka.dto.Like;
import com.moaka.mapper.BookmarkMapper;
import com.moaka.mapper.LikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void deleteLike(int no) {
        likeMapper.deleteLike(no);
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
