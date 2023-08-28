package io.dev.mutex.thesisinfomgmt.exception;

import io.dev.mutex.thesisinfomgmt.model.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ThesisInfoExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handles exceptions that occurred in the service layer
   *
   * @param exception the exception
   * @return response with proper message and status code
   */
  @ExceptionHandler(ThesisInfoServiceException.class)
  public ResponseEntity<Object> handleServiceException(ThesisInfoServiceException exception) {
    ExceptionDTO exceptionDetails = new ExceptionDTO();
    exceptionDetails.setMessage(exception.getMessage());
    exceptionDetails.setField(exception.getField());

    return new ResponseEntity<>(exceptionDetails, exception.getHttpStatus());
  }

  /**
   * Handles all unexpected exceptions
   *
   * @param exception the exception
   * @return response with proper message and status code
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception exception) {
    ExceptionDTO exceptionDetails = new ExceptionDTO();
    exceptionDetails.setMessage(exception.getMessage());
    exceptionDetails.setField(null);

    return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
