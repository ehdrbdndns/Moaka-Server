package com.moaka.mapper;

import com.moaka.dto.Chunk;
import com.moaka.dto.Comment;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface CommentMapper {
    void insertMainCommentOfChunk(Comment comment);
    void insertSubCommentOfChunk(Comment comment);
    void updateGroupNumOfComment(@Param("group_num") int group_num, @Param("no") int no);
    int selectCommentOrderByGroupNum(@Param("group_num") int group_num);
    ArrayList<Comment> selectCommentOfChunk(@Param("chunk_no") int chunk_no);
    boolean isAuthorityOfDeleteComment(@Param("user_no") int user_no, @Param("no") int no);
    void deleteCommentOfChunk(@Param("no") int no);
}
