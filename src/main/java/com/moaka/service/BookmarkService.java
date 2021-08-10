package com.moaka.service;

import com.moaka.common.exception.ErrorCode;
import com.moaka.common.exception.InternalServiceException;
import com.moaka.common.jwt.EncryptionService;
import com.moaka.dto.Chunk;
import com.moaka.dto.User;
import com.moaka.mapper.AuthMapper;
import com.moaka.mapper.BookmarkMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
@Transactional
public class BookmarkService {
    @Autowired
    BookmarkMapper bookmarkMapper;

    public void insertBookmarkToChunk(Chunk chunk, int user_no) throws Exception {
        int section_no = bookmarkMapper.retrieveSectionNoOfDefaultArchive(user_no);
        chunk.setSection_no(section_no);
        chunk.setRegdate(getToday());
        bookmarkMapper.insertBookmarkToChunk(chunk);
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
