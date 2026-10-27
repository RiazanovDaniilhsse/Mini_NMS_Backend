package org.project.catalog.dto;

import java.util.List;
import lombok.Data;

@Data
public class DeviceTypeResponseDto {
  private Long id;
  private String code;
  private String name;
  private String description;
  private List<Long> metricTypeIds;
}
