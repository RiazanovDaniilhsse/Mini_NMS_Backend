package org.project.metric.dto.event;

import lombok.Data;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class NumericMetricEventDto {
  private Long deviceId;
  private String metricTypeCode;
  private BigDecimal value;
  private ZonedDateTime timestamp;
  private String pollStatus;
}