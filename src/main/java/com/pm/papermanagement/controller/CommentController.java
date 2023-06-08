package com.pm.papermanagement.controller;

import com.pm.papermanagement.common.entity.Comment;
import com.pm.papermanagement.common.entity.Paper;
import com.pm.papermanagement.common.exception.BizException;
import com.pm.papermanagement.common.model.Comments;
import com.pm.papermanagement.common.model.Papers;
import com.pm.papermanagement.common.model.ReturnValue;
import com.pm.papermanagement.common.model.param.*;
import com.pm.papermanagement.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CommentController {
    CommentMapper commentMapper;

    @Autowired
    public  void setCommentMapper(CommentMapper commentMapper){
        this.commentMapper = commentMapper;
    }

    @PostMapping(value = "/api/paper/comment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnValue addComment(@RequestBody CommentParam commentParam){
        if(ObjectUtils.isEmpty(commentParam.getContent())){
            throw new BizException("Content不能为空");
        }
        Integer.parseInt(commentParam.getPaper_id());
        commentMapper.addComment(commentParam);
        Comment comment = commentMapper.selectCommentByContent(commentParam.getContent());
        return ReturnValue.generateSuccess(new CommentParamOutput(comment));
    }

    @GetMapping("/api/paper/comment")
    public ReturnValue requestComment(@RequestParam(value = "page_num") int page_num, @RequestParam(value = "page_size") int page_size){
        if(page_num < 0 || page_size < 0){
            throw new BizException("参数不能为负数");
        }
        List<Comment> comments;
        comments = commentMapper.selectCommentBySequence(page_num * page_size, page_size);
        return ReturnValue.generateSuccess(new Comments(comments));
    }

    @RequestMapping(value = "/api/paper/comment/{comment_id}", method = RequestMethod.DELETE)
    public ReturnValue deleteComment(@PathVariable String comment_id){
        commentMapper.deleteCommentById(Integer.parseInt(comment_id));
        //Comment comment = commentMapper.selectCommentById(Integer.parseInt(comment_id));
        CommentParamOutput commentParamOutput = null;
        return ReturnValue.generateSuccess(commentParamOutput);
    }

}
