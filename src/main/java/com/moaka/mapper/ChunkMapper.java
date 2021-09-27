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
    ArrayList<Chunk> retrieveMainChunkBySectionNo(@Param("section_no") int section_no, @Param("user_no") int user_no);
    ArrayList<Chunk> retrieveRelativeChunkByGroupNum(int group_num);
    ArrayList<Chunk> retrieveChunkOfBookmarkByUserNo(int user_no);
    void insertChunk(Chunk chunk);
    void insertRelativeChunk(Chunk chunk);
    int selectRelativeChunkNumber(int group_num);
    void updateGroupNumOfChunk(int chunk_no);
    void updateChunk(Chunk chunk);
    void deleteChunk(Chunk chunk);
    boolean isAuthorityOfDeleteChunk(Chunk chunk);
    boolean isAuthorityOfInsertChunk(@Param("section_no") int section_no, @Param("user_no") int user_no);
    boolean isAuthorityOfUpdateChunk(int no);
}
