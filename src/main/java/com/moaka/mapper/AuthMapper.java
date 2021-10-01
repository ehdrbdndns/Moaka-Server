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
    User retrieveUserById(@Param("id") String id);
    JwtUser retrieveJwtUserById(@Param("id") String id);
    ArrayList<User> retrieveUserListById(@Param("id") String id);
    ArrayList<String> retrieveCategoryListByUserNo(@Param("user_no") int user_no);
    void updateUserInfo(@Param("user_no") int user_no, @Param("profile") String profile, @Param("name") String name);
    void deleteUserCategory(@Param("user_no") int user_no);
    void insertUserCategory(@Param("category") String category, @Param("user_no") int user_no);
}
