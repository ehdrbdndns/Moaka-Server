package com.moaka.service;

import com.moaka.dto.Chunk;
import com.moaka.mapper.BookmarkMapper;
import com.moaka.mapper.ChunkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Service
@Transactional
public class ChunkService {
    @Autowired
    ChunkMapper chunkMapper;

    public void insertChunk(Chunk chunk) throws Exception {
        chunk.setRegdate(getToday());
        chunkMapper.insertChunk(chunk);
    }

    public void updateChunk(Chunk params) throws Exception {
        chunkMapper.updateChunk(params);
    }

    public boolean deleteChunk(Chunk params) throws Exception {
        if(chunkMapper.isAuthorityOfChunk(params)) {
            chunkMapper.deleteChunk(params);
            return true;
        } else {
            return false;
        }
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
