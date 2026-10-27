package org.project.catalog.mapper;

import org.project.catalog.model.Device;
import org.project.catalog.dto.DeviceRequestDto;
import org.project.catalog.dto.DeviceResponseDto;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

  public Device toEntity(DeviceRequestDto dto) {
    Device entity = new Device();
    entity.setName(dto.getName());
    entity.setIpAddress(dto.getIpAddress());
    return entity;
  }

  public DeviceResponseDto toDto(Device entity) {
    DeviceResponseDto dto = new DeviceResponseDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setIpAddress(entity.getIpAddress());

    if (entity.getSite() != null) {
      dto.setSiteId(entity.getSite().getId());
      dto.setSiteName(entity.getSite().getName());
    }

    if (entity.getDeviceType() != null) {
      dto.setDeviceTypeId(entity.getDeviceType().getId());
      dto.setDeviceTypeName(entity.getDeviceType().getName());
    }
    return dto;
  }
}