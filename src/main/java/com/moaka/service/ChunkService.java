package com.moaka.service;

import com.moaka.dto.Chunk;
import com.moaka.mapper.BookmarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
@Transactional
public class ChunkService {
    @Autowired
    BookmarkMapper bookmarkMapper;

    public void insertBookmarkToChunk(Chunk chunk, int user_no) throws Exception {
        chunk.setRegdate(getToday());
        bookmarkMapper.insertBookmarkToChunk(chunk);
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
