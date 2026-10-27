package org.project.catalog.mapper;

import java.util.Collections;
import java.util.stream.Collectors;
import org.project.catalog.dto.DeviceTypeRequestDto;
import org.project.catalog.dto.DeviceTypeResponseDto;
import org.project.catalog.model.DeviceType;
import org.project.metric.model.MetricType;
import org.springframework.stereotype.Component;

@Component
public class DeviceTypeMapper {

  public DeviceType toEntity(DeviceTypeRequestDto dto) {
    DeviceType entity = new DeviceType();
    entity.setCode(dto.getCode());
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    return entity;
  }

  public DeviceTypeResponseDto toDto(DeviceType entity) {
    DeviceTypeResponseDto dto = new DeviceTypeResponseDto();
    dto.setId(entity.getId());
    dto.setCode(entity.getCode());
    dto.setName(entity.getName());
    dto.setDescription(entity.getDescription());

    if (entity.getMetricTypes() != null) {
      dto.setMetricTypeIds(entity.getMetricTypes().stream()
              .map(MetricType::getId)
              .collect(Collectors.toList()));
    } else {
      dto.setMetricTypeIds(Collections.emptyList());
    }
    return dto;
  }
}