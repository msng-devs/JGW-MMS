package com.jaramgroupware.mms.utils.exception;

import com.jaramgroupware.mms.dto.exception.controllerDto.ExceptionMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.util.HashMap;
import java.util.Map;

import static com.jaramgroupware.mms.utils.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({ CustomException.class })
    protected ResponseEntity handleCustomException(CustomException exception, WebRequest request) {

        logger.info("UID = ({}) Request = ({}) Raise = ({})",
                request.getHeader("user_uid"),
                request.getContextPath(),
                exception.getErrorCode().getErrorCode().getClass().getSimpleName()
        );

        return new ResponseEntity(ExceptionMessageDto.builder()
                .status(exception.getErrorCode().getHttpStatus())
                .type(exception.getErrorCode().getErrorCode())
                .title(exception.getErrorCode().getTitle())
                .detail(exception.getErrorCode().getDetail())
                .build()
                , exception.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity handleServerException(Exception exception, WebRequest request) {
        logger.info("UID = ({}) Request = ({}) Raise = ({})",
                request.getHeader("user_uid"),
                request.getContextPath(),
                "INTERNAL_SERVER_ERROR"
        );

        return new ResponseEntity(ExceptionMessageDto.builder()
                .status(INTERNAL_SERVER_ERROR.getHttpStatus())
                .type(INTERNAL_SERVER_ERROR.getErrorCode())
                .title(INTERNAL_SERVER_ERROR.getTitle())
                .detail(INTERNAL_SERVER_ERROR.getDetail())
                .build()
                ,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object processValidationError(MethodArgumentNotValidException exception, WebRequest request) {
        logger.info("UID = ({}) Request = ({}) Raise = ({})",
                request.getHeader("user_uid"),
                request.getContextPath(),
                "MethodArgumentNotValidException"
        );
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder builder = new StringBuilder();
        builder.append("잘못된 입력값이 존재합니다! ");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("필드명 : (");
            builder.append(fieldError.getField());
            builder.append(") 오류 메시지: (");
            builder.append(fieldError.getDefaultMessage());
            builder.append(") 입력된 값: ");
            builder.append(fieldError.getRejectedValue());
            builder.append(" // ");
        }

        return new ResponseEntity(ExceptionMessageDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .type(null)
                .title("METHOD_ARGUMENT_NOT_VALID")
                .detail(builder.toString())
                .build()
                ,HttpStatus.BAD_REQUEST);
    }
}