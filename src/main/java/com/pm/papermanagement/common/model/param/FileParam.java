package com.pm.papermanagement.common.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileParam implements Serializable {
    MultipartFile file;
}
