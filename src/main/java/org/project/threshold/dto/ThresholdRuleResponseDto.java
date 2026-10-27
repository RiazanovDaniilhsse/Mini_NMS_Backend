package org.project.threshold.dto;

import lombok.Data;
import org.project.enums.Operator;
import org.project.enums.Severity;

import java.math.BigDecimal;

@Data
public class ThresholdRuleResponseDto {
  private Long id;
  private Long deviceTypeId;
  private String deviceTypeName;
  private Long metricTypeId;
  private String metricTypeName;
  private Long deviceId;
  private Operator comparator;
  private BigDecimal thresholdValue;
  private Integer durationSec;
  private Severity severity;
  private Boolean enabled;
}