package com.moaka.mapper;

import com.moaka.dto.Bookmark;
import com.moaka.dto.Chunk;
import com.moaka.dto.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BookmarkMapper {
    void insertBookmarkOfChunk(Bookmark bookmark);
    void deleteBookmark(int bookmarkNo);
}
