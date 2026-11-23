package org.project.metric.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
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

  Integer pollIntervalSec;
  private BigDecimal minValue;
  private BigDecimal maxValue;
  private BigDecimal normalLow;
  private BigDecimal normalHigh;
  private BigDecimal step;
  private String snmpType;
  private String kind;
  private String mode;
  private String oid;
  private String stringValues;
  private String staticValue;
}