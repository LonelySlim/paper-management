package com.pm.papermanagement.common.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleParam {
    String title;
    String authors;
    String publisher;
    int year;
}
