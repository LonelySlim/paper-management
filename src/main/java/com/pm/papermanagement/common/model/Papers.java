package com.pm.papermanagement.common.model;

import com.pm.papermanagement.common.entity.Paper;
import com.pm.papermanagement.common.model.param.PaperParamOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Papers implements Serializable {
    List<PaperParamOutput> papers;
    int papernum;

    public Papers(List<Paper> papers,int paperNum){
        this.papers = new ArrayList<>();
        this.papernum = paperNum;
        for (Paper paper:papers
             ) {
            PaperParamOutput paperParamOutput = new PaperParamOutput(paper);
            this.papers.add(paperParamOutput);
        }
    }
}
