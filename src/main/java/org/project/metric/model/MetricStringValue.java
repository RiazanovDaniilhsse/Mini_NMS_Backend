package org.project.metric.model;

import jakarta.persistence.*;
import lombok.Data;
import org.project.catalog.model.Device;
import org.project.enums.PollStatus;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(
        name = "metrics_string",
        uniqueConstraints = @UniqueConstraint(columnNames = {"device_id", "metric_type_id", "timestamp"})
)
public class MetricStringValue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "device_id")
  private Device device;

  @ManyToOne(optional = false)
  @JoinColumn(name = "metric_type_id")
  private MetricType metricType;

  @Column(name = "value", nullable = false)
  private String value;

  @Enumerated(EnumType.STRING)
  private PollStatus pollStatus;

  private ZonedDateTime timestamp;
}