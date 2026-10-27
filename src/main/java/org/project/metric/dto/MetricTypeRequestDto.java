package org.project.metric.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.project.enums.ValueType;

@Data
public class MetricTypeRequestDto {
  @NotBlank(message = "Код метрики обязателен")
  private String code;

  @NotBlank(message = "Название метрики обязательно")
  private String name;

  private String unit;

  @NotNull(message = "Тип значения обязателен")
  private ValueType valueType;
}