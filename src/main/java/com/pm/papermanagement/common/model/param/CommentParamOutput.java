package com.pm.papermanagement.common.model.param;

import com.pm.papermanagement.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentParamOutput implements Serializable {
    String id;
    String content;
    String time;

    public CommentParamOutput(Comment comment){
        if(comment != null){
            id = comment.getId();
            content = comment.getContent();
            time = comment.getTime();
        }
    }
}
