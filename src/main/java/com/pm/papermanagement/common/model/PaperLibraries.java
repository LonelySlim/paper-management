package com.pm.papermanagement.common.model;

import com.pm.papermanagement.common.entity.PaperLibrary;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class PaperLibraries implements Serializable {
    List<PaperLibrary> libraries;
}
