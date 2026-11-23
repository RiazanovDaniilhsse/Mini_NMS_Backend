package org.project.metric.dto;

import java.math.BigDecimal;
import lombok.Data;
import org.project.enums.ValueType;

@Data
public class MetricTypeResponseDto {
  private Long id;
  private String code;
  private String name;
  private String unit;
  private ValueType valueType;
  private Integer pollIntervalSec;
  private BigDecimal minValue;
  private BigDecimal maxValue;
  private BigDecimal normalLow;
  private BigDecimal normalHigh;
  private BigDecimal step;
  private String snmpType;
  private String kind;
  private String mode;
  private String oid;
  private String stringValues;
  private String staticValue;
}