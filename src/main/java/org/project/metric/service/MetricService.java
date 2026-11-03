package org.project.metric.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.catalog.model.Device;
import org.project.catalog.repository.DeviceRepository;
import org.project.enums.PollStatus;
import org.project.exception.exceptions.ResourceNotFoundException;
import org.project.metric.dto.event.BooleanMetricEventDto;
import org.project.metric.dto.event.NumericMetricEventDto;
import org.project.metric.dto.event.StringMetricEventDto;
import org.project.metric.model.MetricBooleanValue;
import org.project.metric.model.MetricNumericValue;
import org.project.metric.model.MetricStringValue;
import org.project.metric.model.MetricType;
import org.project.metric.repository.MetricBooleanValueRepository;
import org.project.metric.repository.MetricNumericValueRepository;
import org.project.metric.repository.MetricStringValueRepository;
import org.project.metric.repository.MetricTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricService {

  private final MetricNumericValueRepository numericRepository;
  private final MetricBooleanValueRepository booleanRepository;
  private final MetricStringValueRepository stringRepository;
  private final DeviceRepository deviceRepository;
  private final MetricTypeRepository metricTypeRepository;

  public void saveNumeric(NumericMetricEventDto event) {
    Device device = findDevice(event.getDeviceId());
    MetricType metricType = findMetricType(event.getMetricTypeCode());
    PollStatus status = parseStatus(event.getPollStatus());
    try {
      MetricNumericValue value = new MetricNumericValue();
      value.setDevice(device);
      value.setMetricType(metricType);
      value.setValue(event.getValue());
      value.setTimestamp(event.getTimestamp());
      value.setPollStatus(status);
      numericRepository.save(value);
      log.info("Saved numeric metric: device={}, metric={}", event.getDeviceId(),
              event.getMetricTypeCode());
    } catch (DataIntegrityViolationException e) {
      log.warn("Duplicate numeric metric ignored: device={}, metric={}", event.getDeviceId(),
              event.getMetricTypeCode());
    }
  }

  public void saveBoolean(BooleanMetricEventDto event) {
    Device device = findDevice(event.getDeviceId());
    MetricType metricType = findMetricType(event.getMetricTypeCode());
    PollStatus status = parseStatus(event.getPollStatus());
    try {
      MetricBooleanValue value = new MetricBooleanValue();
      value.setDevice(device);
      value.setMetricType(metricType);
      value.setValue(event.getValue());
      value.setTimestamp(event.getTimestamp());
      value.setPollStatus(status);
      booleanRepository.save(value);
      log.info("Saved boolean metric: device={}, metric={}", event.getDeviceId(),
              event.getMetricTypeCode());
    } catch (DataIntegrityViolationException e) {
      log.warn("Duplicate boolean metric ignored: device={}, metric={}", event.getDeviceId(),
              event.getMetricTypeCode());
    }
  }

  public void saveString(StringMetricEventDto event) {
    Device device = findDevice(event.getDeviceId());
    MetricType metricType = findMetricType(event.getMetricTypeCode());
    PollStatus status = parseStatus(event.getPollStatus());
    try {
      MetricStringValue value = new MetricStringValue();
      value.setDevice(device);
      value.setMetricType(metricType);
      value.setValue(event.getValue());
      value.setTimestamp(event.getTimestamp());
      value.setPollStatus(status);
      stringRepository.save(value);
      log.info("Saved string metric: device={}, metric={}", event.getDeviceId(),
              event.getMetricTypeCode());
    } catch (DataIntegrityViolationException e) {
      log.warn("Duplicate string metric ignored: device={}, metric={}", event.getDeviceId(),
              event.getMetricTypeCode());
    }
  }

  private Device findDevice(Long id) {
    return deviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Device not found: " + id));
  }

  private MetricType findMetricType(String code) {
    return metricTypeRepository.findByCode(code)
            .orElseThrow(() -> new ResourceNotFoundException("Metric type not found: " + code));
  }

  private PollStatus parseStatus(String statusStr) {
    try {
      return PollStatus.valueOf(statusStr);
    } catch (IllegalArgumentException e) {
      log.warn("Unknown poll status: {}, defaulting to ERROR", statusStr);
      return PollStatus.ERROR;
    }
  }
}