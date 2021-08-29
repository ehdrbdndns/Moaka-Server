package com.moaka.mapper;

import com.moaka.dto.Chunk;
import com.moaka.dto.User;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface ChunkMapper {
    ArrayList<Chunk> retrieveChunkBySectionNo(int section_no);
    void insertChunk(Chunk chunk);
    void updateChunk(Chunk chunk);
    void deleteChunk(Chunk chunk);
    boolean isAuthorityOfChunk(Chunk chunk);
}
