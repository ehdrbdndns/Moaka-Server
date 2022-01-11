package com.moaka.mapper;

import com.moaka.dto.Chunk;
import com.moaka.dto.User;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface ChunkMapper {
    ArrayList<Chunk> retrieveMainChunkBySectionNo(@Param("section_no") int section_no, @Param("user_no") int user_no);

    void insertChunk(Chunk chunk);

    void updateChunk(Chunk chunk);

    void updateChunkFromChrome(@Param("description") String description, @Param("section_no") int section_no, @Param("no") int no);

    void deleteChunk(Chunk chunk);

    boolean isAuthorityOfDeleteChunk(Chunk chunk);

    boolean isAuthorityOfUpdateChunk(@Param("no") int no, @Param("user_no") int user_no);
}
