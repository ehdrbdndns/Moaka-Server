package com.moaka.mapper;

import com.moaka.dto.Room;
import com.moaka.dto.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface RoomMapper {
    void insertRoom(Room room);
}
