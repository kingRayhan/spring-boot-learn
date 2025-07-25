package dev.rayhan.spring_store.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
    Map<String, Object> errorBody = new HashMap<>();
    errorBody.put("timestamp", Instant.now());
    errorBody.put("status", ex.getStatusCode().value());
    errorBody.put("error", ex.getReason());
    errorBody.put("path", request.getRequestURI());

    return ResponseEntity.status(ex.getStatusCode()).body(errorBody);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(
    MethodArgumentNotValidException exception
  ) {

    var errors = new HashMap<String, String>();

    exception.getBindingResult().getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });

    return ResponseEntity.badRequest().body(errors);
  }
}
