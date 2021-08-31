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
    ArrayList<Chunk> retrieveMainChunkBySectionNo(int section_no);
    void insertChunk(Chunk chunk);
    void updateGroupNumOfChunk(int chunk_no);
    void updateChunk(Chunk chunk);
    void deleteChunk(Chunk chunk);
    boolean isAuthorityOfChunk(Chunk chunk);
}
