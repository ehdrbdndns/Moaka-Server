package com.moaka.mapper;

import com.moaka.dto.Section;
import com.moaka.dto.Tag;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface TagMapper {
    void insertSectionTag(Tag tag);
    void insertChunkTag(Tag tag);
    ArrayList<String> retrieveArchiveTagByArchiveNo(int archive_no);
    ArrayList<String> retrieveSectionTagBySectionNo(int section_no);
    ArrayList<String> retrieveChunkTagByChunkNo(int chunk_no);
    void deleteSectionTagBySectionNo(int section_no);
    void deleteChunkTagByChunkNo(int chunk_no);
}
