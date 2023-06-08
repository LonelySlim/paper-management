package com.pm.papermanagement.mapper;

import com.pm.papermanagement.common.entity.Comment;
import com.pm.papermanagement.common.model.param.CommentParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    void addComment(CommentParam commentParam);

    Comment selectCommentByContent(String content);

    List<Comment> selectCommentBySequence(int start, int offset);

    void deleteCommentById(int comment_id);

    //Comment selectCommentById(int i);
}
