package org.project.metric.dto.event;

import lombok.Data;
import java.time.ZonedDateTime;

@Data
public class StringMetricEventDto {
  private Long deviceId;
  private String metricTypeCode;
  private String value;
  private ZonedDateTime timestamp;
  private String pollStatus;
}