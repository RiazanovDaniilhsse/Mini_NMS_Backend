package org.project.emulator.dto;
import java.util.List;
import lombok.Data;

@Data
public class EmulatorMetricDto {
  private String type;
  private String unit;
  private Double min;
  private Double max;
  private Double normalLow;
  private Double normalHigh;
  private Double step;
  private String snmpType;
  private Integer decimals;
  private String kind;
  private String mode;
  private String oid;
  private List<String> values;
  private String value;
}