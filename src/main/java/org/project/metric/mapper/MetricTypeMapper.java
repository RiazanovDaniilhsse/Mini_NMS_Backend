package org.project.metric.mapper;

import org.project.metric.model.MetricType;
import org.project.metric.dto.MetricTypeRequestDto;
import org.project.metric.dto.MetricTypeResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MetricTypeMapper {
  public MetricType toEntity(MetricTypeRequestDto dto) {
    MetricType entity = new MetricType();
    entity.setCode(dto.getCode());
    entity.setName(dto.getName());
    entity.setUnit(dto.getUnit());
    entity.setValueType(dto.getValueType());
    return entity;
  }

  public MetricTypeResponseDto toDto(MetricType entity) {
    MetricTypeResponseDto dto = new MetricTypeResponseDto();
    dto.setId(entity.getId());
    dto.setCode(entity.getCode());
    dto.setName(entity.getName());
    dto.setUnit(entity.getUnit());
    dto.setValueType(entity.getValueType());
    return dto;
  }
}