package com.moaka.service;

import com.moaka.dto.Comment;
import com.moaka.mapper.CommentMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
@Transactional
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    public JSONObject insertCommentOfChunk(Comment comment) {
        JSONObject result = new JSONObject();
        JSONObject commentInfo = new JSONObject();

        String today = getToday();
        comment.setRegdate(today);

        // 1. 댓글인지 대댓글인지 판단
        if (comment.getLayer() == 0) {
            // 댓글
            commentMapper.insertMainCommentOfChunk(comment);
            commentMapper.updateGroupNumOfComment(comment.getNo(), comment.getNo());

            commentInfo.put("content_order", 1);
            commentInfo.put("group_num", comment.getNo());
            commentInfo.put("layer", 0);
        } else {
            // 대댓글
            int contentOrder = commentMapper.selectCommentOrderByGroupNum(comment.getGroup_num()) + 1;
            comment.setContent_order(contentOrder);
            commentMapper.insertSubCommentOfChunk(comment);

            commentInfo.put("content_order", comment.getContent_order());
            commentInfo.put("group_num", comment.getGroup_num());
            commentInfo.put("layer", 1);
        }
        commentInfo.put("no", comment.getNo());
        commentInfo.put("chunk_no", comment.getChunk_no());
        commentInfo.put("user_no", comment.getUser_no());
        commentInfo.put("content", comment.getContent());
        commentInfo.put("profile", comment.getProfile());
        commentInfo.put("name", comment.getName());
        commentInfo.put("regdate", today);

        result.put("comment_info", commentInfo);
        result.put("isSuccess", true);
        return result;
    }

    public JSONObject deleteCommentOfChunk(int user_no, int no) {
        JSONObject result = new JSONObject();
        if(commentMapper.isAuthorityOfDeleteComment(user_no, no)) {
            result.put("isSuccess", true);
            commentMapper.deleteCommentOfChunk(no);
        } else {
            result.put("isSuccess", false);
        }

        return result;
    }

    public String getToday(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
