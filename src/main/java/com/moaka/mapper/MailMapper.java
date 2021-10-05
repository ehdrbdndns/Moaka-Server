package com.moaka.mapper;

import com.moaka.dto.Mail;
import com.moaka.dto.MailCode;
import com.moaka.dto.User;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface MailMapper {
    void insertMailCode(MailCode mailCode);
    void resetMailCode(String regdate);
    boolean isExistCode(@Param("no") int no, @Param("code") int code);
    void expireValidOfMailCode(int no);
}
