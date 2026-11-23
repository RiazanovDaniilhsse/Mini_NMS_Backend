package org.project.emulator.dto;

import java.util.List;
import lombok.Data;

@Data
public class EmulatorConfigDto {
  private List<EmulatorDeviceDto> devices;
}
