package org.project.alarm.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;
import org.project.catalog.model.Device;
import org.project.enums.AlarmStatus;
import org.project.enums.Severity;
import org.project.metric.model.MetricType;
import org.project.threshold.model.ThresholdRule;

@Entity
@Data
@Table(name = "alarms")
public class Alarm {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "device_id")
  private Device device;

  @ManyToOne
  @JoinColumn(name = "rule_id")
  private ThresholdRule rule;

  @ManyToOne
  @JoinColumn(name = "metric_type_id")
  private MetricType metricType;

  @Enumerated(EnumType.STRING)
  private Severity severity;

  @Enumerated(EnumType.STRING)
  private AlarmStatus status;

  private String message;
  private String thresholdSnapshot;
  private ZonedDateTime triggeredAt;
  private ZonedDateTime ackedAt;
  private ZonedDateTime resolvedAt;
  private ZonedDateTime closedAt;
}