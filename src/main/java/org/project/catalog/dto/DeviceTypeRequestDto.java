package org.project.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class DeviceTypeRequestDto {
  @NotBlank(message = "Код типа устройства обязателен")
  @Size(max = 50)
  private String code;

  @NotBlank(message = "Название обязательно")
  @Size(max = 255)
  private String name;

  private String description;

  // Список ID метрик, которые умеет измерять этот тип
  private List<Long> metricTypeIds;
}