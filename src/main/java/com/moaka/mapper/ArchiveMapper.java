package com.moaka.mapper;

import com.moaka.dto.Archive;
import com.moaka.dto.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface ArchiveMapper {
    ArrayList<Archive> retrieveArchiveFromUserNo(int user_no);
}
