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
    boolean isAuthorityOfDeleteArchive(@Param("archive_no") int archive_no, @Param("user_no") int user_no);
    void deleteArchiveFromArchiveNo(@Param("archive_no") int archive_no);
    void insertArchive(Archive archive);
    void insertArchiveGroupFromArchive(@Param("user_no") Integer user_no, @Param("archive_no") int archive_no, @Param("regdate") String regdate);
    ArrayList<Archive> retrieveArchiveBySearch(@Param("param") String param, @Param("user_no") int user_no);
}
