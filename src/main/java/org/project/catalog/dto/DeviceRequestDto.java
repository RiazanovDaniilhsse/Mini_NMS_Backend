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

  private Integer snmpPort;

  private String community = "public";

  private Boolean enabled = true;
}