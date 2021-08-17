package com.moaka.mapper;

import com.moaka.dto.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AuthMapper {
    User login(User user);
    void register(User user);
}
