package org.project.metric.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class MetricEventDto {
  private Long deviceId;
  private String metricTypeCode;
  private BigDecimal value;
  private Boolean booleanValue;
  private ZonedDateTime timestamp;
  private String pollStatus;
}