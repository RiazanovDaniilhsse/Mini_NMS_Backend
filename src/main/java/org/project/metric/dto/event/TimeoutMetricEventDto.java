package org.project.metric.dto.event;

import lombok.Data;
import java.time.ZonedDateTime;

@Data
public class TimeoutMetricEventDto {
  private Long deviceId;
  private String metricTypeCode;
  private ZonedDateTime timestamp;
  private String pollStatus;
}