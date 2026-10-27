package org.project.exception;

import org.project.exception.exceptions.BadRequestException;
import org.project.exception.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 1. Обработка ошибок валидации (@Valid)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
          MethodArgumentNotValidException ex) {
    List<String> details = ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.toList());
    ErrorResponse response = new ErrorResponse(
            "VALIDATION_ERROR",
            "Ошибка валидации входных данных",
            details
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  // 2. Обработка "Не найдено" (404)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
    ErrorResponse response = new ErrorResponse("NOT_FOUND", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  // 3. Обработка "Некорректный запрос" (400)
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
    ErrorResponse response = new ErrorResponse("BAD_REQUEST", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  // 4. Обработка битого JSON (например, неверный Enum)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleJsonErrors(HttpMessageNotReadableException ex) {
    ErrorResponse response = new ErrorResponse(
            "INVALID_JSON",
            "Некорректный формат JSON или недопустимое значение поля"
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  // 5. Все остальные ошибки (500)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
    ErrorResponse response = new ErrorResponse(
            "INTERNAL_SERVER_ERROR",
            "Внутренняя ошибка сервера"
    );
    // В реальном проекте здесь нужно логировать ex.getMessage()
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}