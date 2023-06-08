package com.pm.papermanagement.common.model.param;

import com.pm.papermanagement.common.entity.Paper;
import com.pm.papermanagement.common.entity.PaperLibrary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperParamOutput implements Serializable {
    String id;
    //String library_id;
    String title;
    String authors;
    String publisher;
    int year;
    String source;

    public PaperParamOutput(Paper paper){
        if(paper != null){
            id = paper.getId();
            title = paper.getTitle();
            authors = paper.getAuthors();
            publisher = paper.getPublisher();
            year = paper.getYear();
            source = paper.getSource();
        }
    }
}
