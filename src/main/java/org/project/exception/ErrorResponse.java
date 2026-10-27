package org.project.exception;

import lombok.Data;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ErrorResponse {
  private String code;          // Машинный код (например, NOT_FOUND, VALIDATION_ERROR)
  private String message;       // Человекочитаемое сообщение
  private List<String> details; // Список деталей (для ошибок валидации)
  private ZonedDateTime timestamp;

  public ErrorResponse(String code, String message) {
    this.code = code;
    this.message = message;
    this.timestamp = ZonedDateTime.now();
  }

  public ErrorResponse(String code, String message, List<String> details) {
    this(code, message);
    this.details = details;
  }
}