package org.project.metric.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
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
  private String code;

  private String name;

  private String unit;

  @Enumerated(EnumType.STRING)
  private ValueType valueType;

  @Column(name = "poll_interval_sec", nullable = false)
  private Integer pollIntervalSec = 30;

  @Column(name = "min_value")
  private BigDecimal minValue;

  @Column(name = "max_value")
  private BigDecimal maxValue;

  @Column(name = "normal_low")
  private BigDecimal normalLow;

  @Column(name = "normal_high")
  private BigDecimal normalHigh;

  @Column(name = "step")
  private BigDecimal step = BigDecimal.ONE;

  @Column(name = "snmp_type")
  private String snmpType = "gauge";

  @Column(name = "kind")
  private String kind = "numeric";

  @Column(name = "mode")
  private String mode;

  @Column(name = "oid")
  private String oid;

  @Column(name = "string_values", columnDefinition = "TEXT")
  private String stringValues;

  @Column(name = "static_value")
  private String staticValue;
}
