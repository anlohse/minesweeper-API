package com.anlohse.minesweeper.commons.services;

import com.anlohse.minesweeper.commons.vo.ErrorFieldVO;
import com.anlohse.minesweeper.commons.vo.ErrorVO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ControllerAdvice
public class ErrorService {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorVO handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        ErrorVO errors = new ErrorVO();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.getFields().add(new ErrorFieldVO(fieldName, errorMessage));
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorVO handleOtherExceptions(
            Exception ex) {
        ErrorVO errors = new ErrorVO();
        errors.setMessage(ex.getMessage());
        return errors;
    }

}
