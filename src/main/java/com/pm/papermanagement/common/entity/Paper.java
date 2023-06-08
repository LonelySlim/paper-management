package com.pm.papermanagement.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.io.StringReader;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper implements Serializable {
    String id;
    String library_id;
    String title;
    String authors;
    String publisher;
    int year;
    String source;
}
