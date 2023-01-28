package com.stock.app.stockapplication.exception;

import com.stock.app.stockapplication.model.responsedto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * <P>This class is used to handle the variety of exception occurred in stock application.</P>
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * This method is used to handle the resources not found in the database for the given request.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    /**
     * This method is used to handle the illegal arguments during the request pass.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * <p>This method is used ot validate the request and provide the value able error details to client.</p>
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ErrorMessage> requestValidationException(RequestValidationException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                ex.getRequest().value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false), ex.getErrorMap());
        return new ResponseEntity<ErrorMessage>(message, ex.getRequest());
    }
}
