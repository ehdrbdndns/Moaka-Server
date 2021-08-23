package com.moaka.mapper;

import com.moaka.dto.Tag;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TagMapper {
    void insertSectionTag(Tag tag);
}
