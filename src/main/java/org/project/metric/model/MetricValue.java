package org.project.metric.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;


import java.time.ZonedDateTime;
import org.project.catalog.model.Device;
import org.project.enums.PollStatus;

@Entity
@Data
@Table(name = "metrics")
public class MetricValue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "device_id")
  private Device device;

  @ManyToOne(optional = false)
  @JoinColumn(name = "metric_type_id")
  private MetricType metricType;
  @Column(name = "\"value\"")
  private BigDecimal value;

  private ZonedDateTime timestamp;

  @Enumerated(EnumType.STRING)
  private PollStatus pollStatus;
}