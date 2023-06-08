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

    public Comments(List<Comment> comments){
        this.comments = new ArrayList<>();
        for (Comment comment:
             comments) {
            CommentParamOutput commentParamOutput = new CommentParamOutput(comment);
            this.comments.add(commentParamOutput);
        }
    }
}
