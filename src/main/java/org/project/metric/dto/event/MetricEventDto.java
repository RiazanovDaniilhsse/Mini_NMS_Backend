package org.project.metric.dto.event;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class MetricEventDto {
  private Long deviceId;
  private String metricTypeCode;
  private BigDecimal value;
  private Boolean booleanValue;
  private String stringValue;
  private ZonedDateTime timestamp;
  private String pollStatus;
}
