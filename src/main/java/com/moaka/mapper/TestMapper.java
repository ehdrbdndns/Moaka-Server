package com.moaka.mapper;

import com.moaka.dto.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;

@Repository
@Mapper
public interface TestMapper {
    ArrayList<User> getUser();
    Integer getUserCount();
}
