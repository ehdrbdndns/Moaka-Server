package com.moaka.mapper;

import com.moaka.dto.Archive;
import com.moaka.dto.User;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface ArchiveMapper {
    ArrayList<Archive> retrieveArchiveFromGroup(int user_no);
    ArrayList<Archive> retrieveArchiveFromUserNo(int user_no);
    Archive retrieveArchiveFromArchiveNo(@Param("archive_no") int archive_no, @Param("user_no") int user_no);
}
