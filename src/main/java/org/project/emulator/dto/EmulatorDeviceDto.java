package org.project.emulator.dto;

import java.util.List;
import lombok.Data;

@Data
public class EmulatorDeviceDto {
  private Long id;
  private String name;
  private String type;
  private Integer snmpPort;
  private String community;
  private List<EmulatorMetricDto> metrics;
}