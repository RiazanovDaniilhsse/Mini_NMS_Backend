package org.project.metric.dto.event;

import lombok.Data;
import java.time.ZonedDateTime;

@Data
public class BooleanMetricEventDto {
  private Long deviceId;
  private String metricTypeCode;
  private Boolean value;
  private ZonedDateTime timestamp;
  private String pollStatus;
}