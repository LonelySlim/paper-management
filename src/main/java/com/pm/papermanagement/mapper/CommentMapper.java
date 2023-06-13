package com.pm.papermanagement.mapper;

import com.pm.papermanagement.common.entity.Comment;
import com.pm.papermanagement.common.model.param.CommentParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    void addComment(String content,int paper_id,String owner);

    Comment selectCommentByContent(String content);

    List<Comment> selectCommentBySequence(int start, int offset,int paper_id);

    void deleteCommentById(int comment_id);

    Comment selectCommentById(int comment_id);

    int getCommentNum(int paper_id);
}
