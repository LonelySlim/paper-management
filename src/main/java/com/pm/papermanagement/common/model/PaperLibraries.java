package com.pm.papermanagement.common.model;

import com.pm.papermanagement.common.entity.Paper;
import com.pm.papermanagement.common.entity.PaperLibrary;
import com.pm.papermanagement.common.model.param.PaperLibraryParamOutput;
import com.pm.papermanagement.common.model.param.PaperParamOutput;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaperLibraries implements Serializable {
    List<PaperLibraryParamOutput> libraries;
    int librarynum;

    public PaperLibraries(List<PaperLibrary> paperLibraries, int libraryNum){
        this.libraries = new ArrayList<>();
        this.librarynum = libraryNum;
        for (PaperLibrary paperLibrary : paperLibraries
        ) {
            PaperLibraryParamOutput paperLibraryParamOutput = new PaperLibraryParamOutput(paperLibrary);
            this.libraries.add(paperLibraryParamOutput);
        }
    }

}
