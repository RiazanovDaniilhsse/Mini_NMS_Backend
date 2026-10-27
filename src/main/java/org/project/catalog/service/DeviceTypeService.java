package org.project.catalog.service;

import lombok.RequiredArgsConstructor;
import org.project.catalog.dto.DeviceTypeRequestDto;
import org.project.catalog.dto.DeviceTypeResponseDto;
import org.project.catalog.mapper.DeviceTypeMapper;
import org.project.catalog.model.DeviceType;
import org.project.catalog.repository.DeviceTypeRepository;
import org.project.exception.exceptions.ResourceNotFoundException;
import org.project.metric.model.MetricType;
import org.project.metric.repository.MetricTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceTypeService {

  private final DeviceTypeRepository deviceTypeRepository;
  private final MetricTypeRepository metricTypeRepository;
  private final DeviceTypeMapper mapper;

  public DeviceTypeResponseDto create(DeviceTypeRequestDto dto) {
    DeviceType entity = mapper.toEntity(dto);

    if (dto.getMetricTypeIds() != null && !dto.getMetricTypeIds().isEmpty()) {
      List<MetricType> metrics = metricTypeRepository.findAllById(dto.getMetricTypeIds());
      entity.setMetricTypes(metrics);
    }

    return mapper.toDto(deviceTypeRepository.save(entity));
  }

  public List<DeviceTypeResponseDto> getAll() {
    return deviceTypeRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
  }

  public DeviceTypeResponseDto getById(Long id) {
    DeviceType entity = deviceTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Тип устройства с id " + id + " не найден"));
    return mapper.toDto(entity);
  }

  public DeviceTypeResponseDto update(Long id, DeviceTypeRequestDto dto) {
    DeviceType entity = deviceTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Тип устройства с id " + id + " не найден"));

    entity.setCode(dto.getCode());
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());

    if (dto.getMetricTypeIds() != null) {
      List<MetricType> metrics = metricTypeRepository.findAllById(dto.getMetricTypeIds());
      entity.setMetricTypes(metrics);
    }

    return mapper.toDto(deviceTypeRepository.save(entity));
  }

  public void delete(Long id) {
    if (!deviceTypeRepository.existsById(id)) {
      throw new ResourceNotFoundException("Тип устройства с id " + id + " не найден");
    }
    deviceTypeRepository.deleteById(id);
  }
}