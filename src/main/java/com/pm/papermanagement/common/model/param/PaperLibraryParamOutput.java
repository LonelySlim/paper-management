package com.pm.papermanagement.common.model.param;

import com.pm.papermanagement.common.entity.PaperLibrary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperLibraryParamOutput implements Serializable {
    String id;
    String topic;
    String desc;
    String isPublic;

    public PaperLibraryParamOutput(PaperLibrary paperLibrary){
        this.id = paperLibrary.getId();
        this.topic = paperLibrary.getTopic();
        this.desc = paperLibrary.getDesc();
        this.isPublic = paperLibrary.getIsPublic();
    }


}
