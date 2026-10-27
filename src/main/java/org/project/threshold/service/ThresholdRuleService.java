package org.project.threshold.service;

import lombok.RequiredArgsConstructor;
import org.project.catalog.model.Device;
import org.project.catalog.repository.DeviceRepository;
import org.project.catalog.model.DeviceType;
import org.project.catalog.repository.DeviceTypeRepository;
import org.project.exception.exceptions.BadRequestException;
import org.project.exception.exceptions.ResourceNotFoundException;
import org.project.metric.model.MetricType;
import org.project.metric.repository.MetricTypeRepository;
import org.project.threshold.dto.ThresholdRuleRequestDto;
import org.project.threshold.dto.ThresholdRuleResponseDto;
import org.project.threshold.mapper.ThresholdRuleMapper;
import org.project.threshold.model.ThresholdRule;
import org.project.threshold.repository.ThresholdRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThresholdRuleService {

  private final ThresholdRuleRepository repository;
  private final DeviceTypeRepository deviceTypeRepository;
  private final MetricTypeRepository metricTypeRepository;
  private final DeviceRepository deviceRepository;
  private final ThresholdRuleMapper mapper;

  public ThresholdRuleResponseDto create(ThresholdRuleRequestDto dto) {
    DeviceType deviceType = deviceTypeRepository.findById(dto.getDeviceTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Тип устройства с id " + dto.getDeviceTypeId() + " не найден"));

    MetricType metricType = metricTypeRepository.findById(dto.getMetricTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Метрика с id " + dto.getMetricTypeId() + " не найдена"));

    // БИЗНЕС-ВАЛИДАЦИЯ: умеет ли этот тип устройства измерять эту метрику?
    if (deviceType.getMetricTypes() == null || !deviceType.getMetricTypes().contains(metricType)) {
      throw new BadRequestException("Тип устройства '" + deviceType.getCode() + "' не умеет измерять метрику '" + metricType.getCode() + "'");
    }

    ThresholdRule entity = mapper.toEntity(dto);
    entity.setDeviceType(deviceType);
    entity.setMetricType(metricType);

    if (dto.getDeviceId() != null) {
      Device device = deviceRepository.findById(dto.getDeviceId())
              .orElseThrow(() -> new ResourceNotFoundException("Устройство с id " + dto.getDeviceId() + " не найдено"));
      entity.setDevice(device);
    }

    return mapper.toDto(repository.save(entity));
  }

  public List<ThresholdRuleResponseDto> getAll() {
    return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
  }

  public ThresholdRuleResponseDto getById(Long id) {
    ThresholdRule entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Правило с id " + id + " не найдено"));
    return mapper.toDto(entity);
  }

  public ThresholdRuleResponseDto update(Long id, ThresholdRuleRequestDto dto) {
    ThresholdRule entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Правило с id " + id + " не найдено"));

    DeviceType deviceType = deviceTypeRepository.findById(dto.getDeviceTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Тип устройства с id " + dto.getDeviceTypeId() + " не найден"));

    MetricType metricType = metricTypeRepository.findById(dto.getMetricTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Метрика с id " + dto.getMetricTypeId() + " не найдена"));

    // БИЗНЕС-ВАЛИДАЦИЯ
    if (deviceType.getMetricTypes() == null || !deviceType.getMetricTypes().contains(metricType)) {
      throw new BadRequestException("Тип устройства '" + deviceType.getCode() + "' не умеет измерять метрику '" + metricType.getCode() + "'");
    }

    entity.setDeviceType(deviceType);
    entity.setMetricType(metricType);

    if (dto.getDeviceId() != null) {
      Device device = deviceRepository.findById(dto.getDeviceId())
              .orElseThrow(() -> new ResourceNotFoundException("Устройство с id " + dto.getDeviceId() + " не найдено"));
      entity.setDevice(device);
    } else {
      entity.setDevice(null);
    }

    entity.setComparator(dto.getComparator());
    entity.setThresholdValue(dto.getThresholdValue());
    entity.setDurationSec(dto.getDurationSec());
    entity.setSeverity(dto.getSeverity());
    entity.setEnabled(dto.getEnabled());

    return mapper.toDto(repository.save(entity));
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Правило с id " + id + " не найдено");
    }
    repository.deleteById(id);
  }
}