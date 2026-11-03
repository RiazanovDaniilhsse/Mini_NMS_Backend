package org.project.metric.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.project.catalog.model.Device;
import org.project.catalog.repository.DeviceRepository;
import org.project.enums.ValueType;
import org.project.metric.dto.event.NumericMetricEventDto;
import org.project.metric.model.MetricBooleanValue;
import org.project.metric.model.MetricNumericValue;
import org.project.metric.model.MetricType;
import org.project.metric.repository.MetricBooleanValueRepository;
import org.project.metric.repository.MetricNumericValueRepository;
import org.project.metric.repository.MetricTypeRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {

  @Mock
  private MetricNumericValueRepository numericRepository;
  @Mock
  private MetricBooleanValueRepository booleanRepository;
  @Mock
  private DeviceRepository deviceRepository;
  @Mock
  private MetricTypeRepository metricTypeRepository;

  @InjectMocks
  private MetricService metricService;

  @Test
  void saveFromEvent_ShouldSaveToNumericRepository_WhenValueTypeIsNumber() {
    // Given
    NumericMetricEventDto event = new NumericMetricEventDto();
    event.setDeviceId(1L);
    event.setMetricTypeCode("CPU_LOAD");
    event.setValue(new BigDecimal("85.5"));
    event.setTimestamp(ZonedDateTime.now());
    event.setPollStatus("SUCCESS");

    Device device = new Device();
    MetricType metricType = new MetricType();
    metricType.setValueType(ValueType.NUMBER);

    when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
    when(metricTypeRepository.findByCode("CPU_LOAD")).thenReturn(Optional.of(metricType));

    // When
    metricService.saveFromEvent(event);

    // Then
    verify(numericRepository, times(1)).save(any(MetricNumericValue.class));
    verify(booleanRepository, never()).save(any(MetricBooleanValue.class));
  }

  @Test
  void saveFromEvent_ShouldSaveToBooleanRepository_WhenValueTypeIsBoolean() {
    // Given
    NumericMetricEventDto event = new NumericMetricEventDto();
    event.setDeviceId(1L);
    event.setMetricTypeCode("DOOR_OPEN");
    event.setBooleanValue(true);
    event.setTimestamp(ZonedDateTime.now());
    event.setPollStatus("SUCCESS");

    Device device = new Device();
    MetricType metricType = new MetricType();
    metricType.setValueType(ValueType.BOOLEAN);

    when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
    when(metricTypeRepository.findByCode("DOOR_OPEN")).thenReturn(Optional.of(metricType));

    // When
    metricService.saveFromEvent(event);

    // Then
    verify(booleanRepository, times(1)).save(any(MetricBooleanValue.class));
    verify(numericRepository, never()).save(any(MetricNumericValue.class));
  }
}