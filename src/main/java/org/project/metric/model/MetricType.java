package org.project.metric.model;


import jakarta.persistence.*;
import lombok.Data;
import org.project.enums.ValueType;

@Entity
@Data
@Table(name = "metric_types")
public class MetricType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String code; // CPU_LOAD, TEMPERATURE, OPTICAL_SIGNAL...

  private String name;
  private String unit; // %, C, dBm...

  @Enumerated(EnumType.STRING)
  private ValueType valueType;
}
