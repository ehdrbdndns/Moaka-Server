package com.moaka.mapper;

import com.moaka.dto.Like;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LikeMapper {
    void insertLikeOfArchive(Like like);
    void insertLikeOfChunk(Like like);
    void deleteArchiveLike(int no);
    void deleteChunkLike(int no);
}
