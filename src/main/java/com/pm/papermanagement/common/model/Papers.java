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

    public Papers(List<Paper> papers){
        this.papers = new ArrayList<>();
        for (Paper paper:papers
             ) {
            PaperParamOutput paperParamOutput = new PaperParamOutput(paper);
            this.papers.add(paperParamOutput);
        }
    }
}
