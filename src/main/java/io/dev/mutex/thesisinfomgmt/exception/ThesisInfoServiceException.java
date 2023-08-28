package io.dev.mutex.thesisinfomgmt.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ThesisInfoServiceException extends RuntimeException {

  private final String field;

  private final HttpStatus httpStatus;

  public ThesisInfoServiceException(String message, HttpStatus httpStatus) {
    super(message);
    this.field = null;
    this.httpStatus = httpStatus;
  }

  public ThesisInfoServiceException(String message, String field, HttpStatus httpStatus) {
    super(message);
    this.field = field;
    this.httpStatus = httpStatus;
  }
}
