package org.project.threshold.service;

import org.project.catalog.model.Device;
import org.project.catalog.repository.DeviceRepository;
import org.project.catalog.model.DeviceType;
import org.project.catalog.repository.DeviceTypeRepository;
import org.project.enums.Operator;
import org.project.enums.Severity;
import org.project.exception.exceptions.BadRequestException;
import org.project.exception.exceptions.ResourceNotFoundException;
import org.project.metric.model.MetricType;
import org.project.metric.repository.MetricTypeRepository;
import org.project.threshold.dto.ThresholdRuleRequestDto;
import org.project.threshold.dto.ThresholdRuleResponseDto;
import org.project.threshold.mapper.ThresholdRuleMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import org.project.threshold.model.ThresholdRule;
import org.project.threshold.repository.ThresholdRuleRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ThresholdRuleServiceTest {

  @Mock
  private ThresholdRuleRepository repository;
  @Mock
  private DeviceTypeRepository deviceTypeRepository;
  @Mock
  private MetricTypeRepository metricTypeRepository;
  @Mock
  private DeviceRepository deviceRepository;
  @Mock
  private ThresholdRuleMapper mapper;

  @InjectMocks
  private ThresholdRuleService service;

  @Test
  void create_ShouldThrowException_WhenDeviceTypeNotFound() {
    // Given
    ThresholdRuleRequestDto dto = new ThresholdRuleRequestDto();
    dto.setDeviceTypeId(999L);
    dto.setMetricTypeId(1L);

    when(deviceTypeRepository.findById(999L)).thenReturn(Optional.empty());

    // When & Then
    assertThatThrownBy(() -> service.create(dto))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Тип устройства с id 999 не найден");
  }

  @Test
  void create_ShouldThrowException_WhenMetricTypeNotFound() {
    // Given
    ThresholdRuleRequestDto dto = new ThresholdRuleRequestDto();
    dto.setDeviceTypeId(1L);
    dto.setMetricTypeId(999L);

    DeviceType deviceType = new DeviceType();
    when(deviceTypeRepository.findById(1L)).thenReturn(Optional.of(deviceType));
    when(metricTypeRepository.findById(999L)).thenReturn(Optional.empty());

    // When & Then
    assertThatThrownBy(() -> service.create(dto))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Метрика с id 999 не найдена");
  }

  @Test
  void create_ShouldThrowException_WhenMetricNotSupportedByDeviceType() {
    // Given
    ThresholdRuleRequestDto dto = new ThresholdRuleRequestDto();
    dto.setDeviceTypeId(1L);
    dto.setMetricTypeId(1L);

    DeviceType deviceType = new DeviceType();
    deviceType.setCode("ENV_SENSOR");
    deviceType.setMetricTypes(Collections.emptyList()); // У датчика нет метрик

    MetricType metricType = new MetricType();
    metricType.setCode("CPU_LOAD");

    when(deviceTypeRepository.findById(1L)).thenReturn(Optional.of(deviceType));
    when(metricTypeRepository.findById(1L)).thenReturn(Optional.of(metricType));

    // When & Then
    assertThatThrownBy(() -> service.create(dto))
            .isInstanceOf(BadRequestException.class)
            .hasMessageContaining("Тип устройства 'ENV_SENSOR' не умеет измерять метрику 'CPU_LOAD'");
  }

  @Test
  void create_ShouldSaveRule_WhenAllValid() {
    // Given
    ThresholdRuleRequestDto dto = new ThresholdRuleRequestDto();
    dto.setDeviceTypeId(1L);
    dto.setMetricTypeId(1L);
    dto.setComparator(Operator.GT);
    dto.setThresholdValue(new BigDecimal("85.0"));
    dto.setSeverity(Severity.WARNING);
    dto.setDurationSec(60);
    dto.setEnabled(true);

    DeviceType deviceType = new DeviceType();
    deviceType.setCode("ROUTER");

    MetricType metricType = new MetricType();
    metricType.setCode("CPU_LOAD");

    // Связываем метрику с типом устройства
    deviceType.setMetricTypes(Collections.singletonList(metricType));

    when(deviceTypeRepository.findById(1L)).thenReturn(Optional.of(deviceType));
    when(metricTypeRepository.findById(1L)).thenReturn(Optional.of(metricType));

    ThresholdRule entityToSave = new ThresholdRule();
    when(mapper.toEntity(dto)).thenReturn(entityToSave);

    ThresholdRule savedEntity = new ThresholdRule();
    savedEntity.setId(1L);
    when(repository.save(any(ThresholdRule.class))).thenReturn(savedEntity);

    ThresholdRuleResponseDto responseDto = new ThresholdRuleResponseDto();
    responseDto.setId(1L);
    when(mapper.toDto(savedEntity)).thenReturn(responseDto);

    // When
    ThresholdRuleResponseDto result = service.create(dto);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(1L);
    verify(repository, times(1)).save(any(ThresholdRule.class));
  }
}