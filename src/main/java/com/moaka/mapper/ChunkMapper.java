package com.moaka.mapper;

import com.moaka.dto.Chunk;
import com.moaka.dto.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface ChunkMapper {
    ArrayList<Chunk> retrieveChunkBySectionNo(int section_no);
}
