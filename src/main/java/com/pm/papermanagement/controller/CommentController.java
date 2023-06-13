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
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
//@CrossOrigin
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
        int paper_id = Integer.parseInt(commentParam.getPaper_id());
        String owner = SecurityUtils.getSubject().getPrincipal().toString();
        commentMapper.addComment(commentParam.getContent(),paper_id,owner);
        Comment comment = commentMapper.selectCommentByContent(commentParam.getContent());
        return ReturnValue.generateSuccess(new CommentParamOutput(comment));
    }

    @GetMapping("/api/paper/comment")
    public ReturnValue requestComment(@RequestParam(value = "paper_id") String paper_id, @RequestParam(value = "page_num") int page_num, @RequestParam(value = "page_size") int page_size){
        page_num -= 1;
        if(page_num < 0 || page_size < 0){
            throw new BizException("参数不能为负数");
        }
        List<Comment> comments;
        comments = commentMapper.selectCommentBySequence(page_num * page_size, page_size,Integer.parseInt(paper_id));
        int commentNum = commentMapper.getCommentNum(Integer.parseInt(paper_id));
        return ReturnValue.generateSuccess(new Comments(comments,commentNum));
    }

    @RequestMapping(value = "/api/paper/comment/{comment_id}", method = RequestMethod.DELETE)
    public ReturnValue deleteComment(@PathVariable String comment_id){
        String owner = SecurityUtils.getSubject().getPrincipal().toString();
        Comment comment = commentMapper.selectCommentById(Integer.parseInt(comment_id));
        if(!owner.equals(comment.getOwner())){
            return ReturnValue.generate("800","无权限",null);
        }
        commentMapper.deleteCommentById(Integer.parseInt(comment_id));
        //Comment comment = commentMapper.selectCommentById(Integer.parseInt(comment_id));
        CommentParamOutput commentParamOutput = null;
        return ReturnValue.generateSuccess(commentParamOutput);
    }

}
