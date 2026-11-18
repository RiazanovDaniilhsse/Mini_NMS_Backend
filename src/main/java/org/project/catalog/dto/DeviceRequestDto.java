package org.project.catalog.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeviceRequestDto {

  @NotBlank(message = "Имя устройства обязательно")
  private String name;

  private String ipAddress;

  private Long siteId;

  @NotNull(message = "ID типа устройства обязателен")
  private Long deviceTypeId;

  @Min(value = 1, message = "Порт должен быть от 1 до 65535")
  @Max(value = 65535, message = "Порт должен быть от 1 до 65535")
  private Integer port;
  private Boolean enabled = true;
}