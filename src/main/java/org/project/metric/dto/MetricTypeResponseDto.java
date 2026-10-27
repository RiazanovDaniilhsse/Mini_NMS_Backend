package org.project.metric.dto;

import lombok.Data;
import org.project.enums.ValueType;

@Data
public class MetricTypeResponseDto {
  private Long id;
  private String code;
  private String name;
  private String unit;
  private ValueType valueType;
}