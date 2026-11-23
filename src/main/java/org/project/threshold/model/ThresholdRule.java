package org.project.threshold.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import org.project.catalog.model.Device;
import org.project.catalog.model.DeviceType;
import org.project.enums.Operator;
import org.project.enums.Severity;
import org.project.metric.model.MetricType;


@Entity
@Data
@Table(name = "threshold_rules")
public class ThresholdRule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "device_type_id")
  private DeviceType deviceType;

  @ManyToOne(optional = false)
  @JoinColumn(name = "metric_type_id")
  private MetricType metricType;

  @ManyToOne
  @JoinColumn(name = "device_id")
  private Device device;

  @Enumerated(EnumType.STRING)
  private Operator comparator;

  @Column(name = "numeric_value")
  private BigDecimal numericValue;

  @Column(name = "boolean_value")
  private Boolean booleanValue;

  @Column(name = "string_values")
  private List<String> stringValues; //значения показывающие ненормальное состояние//

  private Integer durationSec;

  @Enumerated(EnumType.STRING)
  private Severity severity;

  private Boolean enabled;
}
