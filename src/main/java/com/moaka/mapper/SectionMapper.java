package com.moaka.mapper;

import com.moaka.dto.Section;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface SectionMapper {
    ArrayList<Section> retrieveSectionFromArchiveNo(int archive_no);
    void insertSection(Section section);
}
