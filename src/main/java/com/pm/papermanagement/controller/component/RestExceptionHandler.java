package com.pm.papermanagement.controller.component;

import com.pm.papermanagement.common.exception.BizException;
import com.pm.papermanagement.common.model.ReturnValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BizException.class)
    public ReturnValue bizException(BizException e) {
        return ReturnValue.generate("405", e.getMessage(), null);
    }
    @ExceptionHandler(NumberFormatException.class)
    public  ReturnValue numberFormatException(NumberFormatException e){
        return ReturnValue.generate("500",e.getMessage(),null);
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ReturnValue sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        return ReturnValue.generate("700",e.getMessage(),null);
    }
}
