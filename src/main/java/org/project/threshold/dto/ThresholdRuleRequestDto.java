package org.project.threshold.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.project.enums.Operator;
import org.project.enums.Severity;

import java.math.BigDecimal;

@Data
public class ThresholdRuleRequestDto {

  @NotNull(message = "ID типа устройства обязателен")
  private Long deviceTypeId;

  @NotNull(message = "ID типа метрики обязателен")
  private Long metricTypeId;

  private Long deviceId; // Может быть null (правило на весь класс устройств)

  @NotNull(message = "Оператор сравнения обязателен")
  private Operator comparator;

  @NotNull(message = "Значение порога обязательно")
  private BigDecimal thresholdValue; // Убрали @Positive, чтобы можно было ставить 0

  @Min(value = 0, message = "Длительность не может быть отрицательной")
  private Integer durationSec = 0;

  @NotNull(message = "Важность обязательна")
  private Severity severity;

  private Boolean enabled = true;
}