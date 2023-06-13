package com.pm.papermanagement.common.model;

import com.pm.papermanagement.common.entity.Comment;
import com.pm.papermanagement.common.model.param.CommentParamOutput;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Comments implements Serializable {
    List<CommentParamOutput> comments;
    int commentnum;

    public Comments(List<Comment> comments,int commentNum){
        this.comments = new ArrayList<>();
        this.commentnum = commentNum;
        for (Comment comment:
             comments) {
            CommentParamOutput commentParamOutput = new CommentParamOutput(comment);
            this.comments.add(commentParamOutput);
        }
    }
}
