package com.pm.papermanagement.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.io.StringReader;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperLibrary implements Serializable {
    String id;
    String topic;
    String desc;
    String isPublic;
    String owner;
}
