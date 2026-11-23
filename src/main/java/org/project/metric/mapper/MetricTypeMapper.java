package org.project.metric.mapper;

import java.math.BigDecimal;
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
    entity.setPollIntervalSec(dto.getPollIntervalSec());
    entity.setMinValue(dto.getMinValue());
    entity.setMaxValue(dto.getMaxValue());
    entity.setNormalLow(dto.getNormalLow());
    entity.setNormalHigh(dto.getNormalHigh());
    entity.setStep(dto.getStep() != null ? dto.getStep() : BigDecimal.ONE);
    entity.setKind(dto.getKind() != null ? dto.getKind() : "numeric");
    if (dto.getSnmpType() != null) {
      entity.setSnmpType(dto.getSnmpType());
    } else if ("counter".equals(entity.getKind())) {
      entity.setSnmpType("counter64");
    } else {
      entity.setSnmpType("gauge");
    }
    entity.setMode(dto.getMode());
    entity.setOid(dto.getOid());
    entity.setStringValues(dto.getStringValues());
    entity.setStaticValue(dto.getStaticValue());
    return entity;
  }

  public MetricTypeResponseDto toDto(MetricType entity) {
    MetricTypeResponseDto dto = new MetricTypeResponseDto();
    dto.setId(entity.getId());
    dto.setCode(entity.getCode());
    dto.setName(entity.getName());
    dto.setUnit(entity.getUnit());
    dto.setValueType(entity.getValueType());
    dto.setPollIntervalSec(entity.getPollIntervalSec());
    dto.setMinValue(entity.getMinValue());
    dto.setMaxValue(entity.getMaxValue());
    dto.setNormalLow(entity.getNormalLow());
    dto.setNormalHigh(entity.getNormalHigh());
    dto.setStep(entity.getStep());
    dto.setSnmpType(entity.getSnmpType());
    dto.setKind(entity.getKind());
    dto.setMode(entity.getMode());
    dto.setOid(entity.getOid());
    dto.setStringValues(entity.getStringValues());
    dto.setStaticValue(entity.getStaticValue());
    return dto;
  }
}