package org.project.emulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.catalog.model.Device;
import org.project.catalog.repository.DeviceRepository;
import org.project.emulator.dto.EmulatorConfigDto;
import org.project.emulator.dto.EmulatorDeviceDto;
import org.project.emulator.dto.EmulatorMetricDto;
import org.project.metric.model.MetricType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emulator")
@RequiredArgsConstructor
@Tag(name = "Emulator Config", description = "Конфигурация для эмулятора")
public class EmulatorConfigController {

  private final DeviceRepository deviceRepository;

  @GetMapping("/config")
  public EmulatorConfigDto getConfig() {
    EmulatorConfigDto config = new EmulatorConfigDto();
    config.setDevices(deviceRepository.findAll().stream()
            .map(this::toDeviceDto)
            .toList());
    return config;
  }

  private EmulatorDeviceDto toDeviceDto(Device device) {
    EmulatorDeviceDto dto = new EmulatorDeviceDto();
    dto.setId(device.getId());
    dto.setName(device.getName());
    dto.setType(device.getDeviceType().getCode());
    dto.setSnmpPort(device.getSnmpPort());
    dto.setCommunity(device.getCommunity());

    List<EmulatorMetricDto> metrics = device.getDeviceType().getMetricTypes().stream()
            .map(this::toMetricDto)
            .toList();
    dto.setMetrics(metrics);
    return dto;
  }

  private EmulatorMetricDto toMetricDto(MetricType metric) {
    EmulatorMetricDto dto = new EmulatorMetricDto();
    dto.setType(metric.getCode());
    dto.setUnit(metric.getUnit());
    dto.setMin(metric.getMinValue() != null ? metric.getMinValue().doubleValue() : null);
    dto.setMax(metric.getMaxValue() != null ? metric.getMaxValue().doubleValue() : null);
    dto.setNormalLow(metric.getNormalLow() != null ? metric.getNormalLow().doubleValue() : null);
    dto.setNormalHigh(metric.getNormalHigh() != null ? metric.getNormalHigh().doubleValue() : null);
    dto.setStep(metric.getStep() != null ? metric.getStep().doubleValue() : 3.0);
    dto.setSnmpType(metric.getSnmpType());
    dto.setKind(metric.getKind());
    dto.setMode(metric.getMode());
    dto.setOid(metric.getOid());
    dto.setDecimals(0);

    // Для строковых метрик: парсим JSON-строку в список
    if (metric.getStringValues() != null && !metric.getStringValues().isBlank()) {
      try {
        dto.setValues(new ObjectMapper().readValue(metric.getStringValues(), List.class));
      } catch (Exception e) {
        dto.setValues(List.of());
      }
    }
    dto.setValue(metric.getStaticValue());
    return dto;
  }
}