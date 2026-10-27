package org.project.catalog.dto;

import lombok.Data;

@Data
public class DeviceResponseDto {
  private Long id;
  private String name;
  private String ipAddress;
  private Long siteId;
  private String siteName; // Для удобства фронтенда
  private Long deviceTypeId;
  private String deviceTypeName;
}