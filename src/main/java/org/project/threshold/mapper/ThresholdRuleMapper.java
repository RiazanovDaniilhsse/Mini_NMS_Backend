package org.project.threshold.mapper;

import org.project.threshold.model.ThresholdRule;
import org.project.threshold.dto.ThresholdRuleRequestDto;
import org.project.threshold.dto.ThresholdRuleResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ThresholdRuleMapper {

  public ThresholdRule toEntity(ThresholdRuleRequestDto dto) {
    ThresholdRule entity = new ThresholdRule();
    entity.setComparator(dto.getComparator());
    entity.setThresholdValue(dto.getThresholdValue());
    entity.setDurationSec(dto.getDurationSec());
    entity.setSeverity(dto.getSeverity());
    entity.setEnabled(dto.getEnabled());
    return entity;
  }

  public ThresholdRuleResponseDto toDto(ThresholdRule entity) {
    ThresholdRuleResponseDto dto = new ThresholdRuleResponseDto();
    dto.setId(entity.getId());
    dto.setComparator(entity.getComparator());
    dto.setThresholdValue(entity.getThresholdValue());
    dto.setDurationSec(entity.getDurationSec());
    dto.setSeverity(entity.getSeverity());
    dto.setEnabled(entity.getEnabled());

    if (entity.getDeviceType() != null) {
      dto.setDeviceTypeId(entity.getDeviceType().getId());
      dto.setDeviceTypeName(entity.getDeviceType().getName());
    }
    if (entity.getMetricType() != null) {
      dto.setMetricTypeId(entity.getMetricType().getId());
      dto.setMetricTypeName(entity.getMetricType().getName());
    }
    if (entity.getDevice() != null) {
      dto.setDeviceId(entity.getDevice().getId());
    }
    return dto;
  }
}