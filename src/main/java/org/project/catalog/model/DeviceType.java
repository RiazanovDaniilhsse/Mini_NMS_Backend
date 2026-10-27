package org.project.catalog.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import org.project.metric.model.MetricType;

@Entity
@Data
@Table(name = "device_types")
public class DeviceType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String code; // ROUTER, LTE_BS, OLT, ENV_SENSOR, UPS

  private String name;
  private String description;

  // Связь M:N - какие метрики умеет измерять этот тип устройства
  @ManyToMany
  @JoinTable(
          name = "device_type_metric_type",
          joinColumns = @JoinColumn(name = "device_type_id"),
          inverseJoinColumns = @JoinColumn(name = "metric_type_id")
  )
  private List<MetricType> metricTypes;
}
