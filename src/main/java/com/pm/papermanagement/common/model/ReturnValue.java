package com.pm.papermanagement.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class ReturnValue implements Serializable {
    String code;
    String error_msg;
    Object data;

    static public ReturnValue generateSuccess(Serializable data) {
        return new ReturnValue("200", "操作成功", data);
    }

    static public ReturnValue generate(String code, String error_msg, Serializable data) {
        return new ReturnValue(code, error_msg, data);
    }
}
