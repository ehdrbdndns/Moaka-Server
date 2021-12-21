package com.moaka.mapper;

import com.moaka.dto.JwtUser;
import com.moaka.dto.User;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface AuthMapper {
    User login(User user);
    void register(User user);
    void changeUserPwdByEmail(@Param("pwd") String pwd, @Param("email") String email);
    void deleteUserByNo(int userNo);
}
