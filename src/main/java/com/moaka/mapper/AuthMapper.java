package com.moaka.mapper;

import com.moaka.dto.User;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AuthMapper {
    User login(User user);
    void register(User user);
    User retrieveUserById(@Param("id") String id);
}
