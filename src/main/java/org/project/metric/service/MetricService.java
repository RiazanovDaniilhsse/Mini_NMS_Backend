package org.project.metric.service;

import lombok.RequiredArgsConstructor;
import org.project.catalog.model.Device;
import org.project.catalog.repository.DeviceRepository;
import org.project.enums.PollStatus;
import org.project.enums.ValueType;
import org.project.exception.exceptions.ResourceNotFoundException;
import org.project.metric.dto.MetricEventDto;
import org.project.metric.model.MetricBooleanValue;
import org.project.metric.model.MetricNumericValue;
import org.project.metric.model.MetricType;
import org.project.metric.repository.MetricBooleanValueRepository;
import org.project.metric.repository.MetricNumericValueRepository;
import org.project.metric.repository.MetricTypeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricService {

  private final MetricNumericValueRepository numericRepository;
  private final MetricBooleanValueRepository booleanRepository;
  private final DeviceRepository deviceRepository;
  private final MetricTypeRepository metricTypeRepository;

  public void saveFromEvent(MetricEventDto event) {
    Device device = deviceRepository.findById(event.getDeviceId())
            .orElseThrow(() -> new ResourceNotFoundException("Device not found: " + event.getDeviceId()));

    MetricType metricType = metricTypeRepository.findByCode(event.getMetricTypeCode())
            .orElseThrow(() -> new ResourceNotFoundException("Metric type not found: " + event.getMetricTypeCode()));

    PollStatus status = PollStatus.valueOf(event.getPollStatus());

    if (metricType.getValueType() == ValueType.NUMBER) {
      MetricNumericValue numericValue = new MetricNumericValue();
      numericValue.setDevice(device);
      numericValue.setMetricType(metricType);
      numericValue.setValue(event.getValue());
      numericValue.setTimestamp(event.getTimestamp());
      numericValue.setPollStatus(status);
      numericRepository.save(numericValue);
    } else {
      MetricBooleanValue booleanValue = new MetricBooleanValue();
      booleanValue.setDevice(device);
      booleanValue.setMetricType(metricType);
      booleanValue.setValue(event.getBooleanValue());
      booleanValue.setTimestamp(event.getTimestamp());
      booleanValue.setPollStatus(status);
      booleanRepository.save(booleanValue);
    }
  }
}