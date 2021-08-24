package com.moaka.mapper;

import com.moaka.dto.Section;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface SectionMapper {
    void updateSection(Section section);
    ArrayList<Section> retrieveSectionByArchiveNo(int archive_no);
    void insertSection(Section section);
    void deleteSection(int no);
}
