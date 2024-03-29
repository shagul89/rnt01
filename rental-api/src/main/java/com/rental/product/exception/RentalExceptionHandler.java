package com.rental.product.exception;

import com.rental.product.mapper.ResponseVO;
import com.rental.product.util.AppUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RentalExceptionHandler extends ResponseEntityExceptionHandler {

    private final AppUtil appUtil;

    @Autowired
    public RentalExceptionHandler(AppUtil appUtil) {
        this.appUtil = appUtil;
    }

    @ExceptionHandler(RentalException.class)
    public ResponseEntity<ResponseVO> handleApplicationException(final RentalException exception, final HttpServletRequest request){
        ResponseVO responseVO = appUtil.failedResponse("SERVICE_EXCEPTION", exception.getMessage());
        log.error(String.format("Exception occurred in : %s ", responseVO.getErrorDetails()));
        return new ResponseEntity<>(responseVO, exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVO> handleException(final Exception exception, final HttpServletRequest request){
        ResponseVO responseVO = appUtil.failedResponse("SERVICE_EXCEPTION", exception.getMessage());
        log.error(String.format("Exception occurred in : %s ", responseVO.getErrorDetails()));
        RentalException recruitmentException = new RentalException(responseVO.getErrorDetails(), HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception.getStackTrace());
        log.error(String.format("Exception occurred in : %s ", responseVO.getErrorDetails()));
        return new ResponseEntity<>(responseVO, recruitmentException.getHttpStatus());
    }
}
