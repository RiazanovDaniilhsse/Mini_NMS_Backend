package org.project.catalog.service;

import lombok.RequiredArgsConstructor;
import org.project.catalog.dto.DeviceRequestDto;
import org.project.catalog.dto.DeviceResponseDto;
import org.project.catalog.mapper.DeviceMapper;
import org.project.catalog.model.Device;
import org.project.catalog.model.DeviceType;
import org.project.catalog.model.Site;
import org.project.catalog.repository.DeviceRepository;
import org.project.catalog.repository.DeviceTypeRepository;
import org.project.catalog.repository.SiteRepository;
import org.project.exception.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

  private final DeviceRepository deviceRepository;
  private final SiteRepository siteRepository;
  private final DeviceTypeRepository deviceTypeRepository;
  private final DeviceMapper mapper;

  public DeviceResponseDto create(DeviceRequestDto dto) {
    Device entity = mapper.toEntity(dto);

    DeviceType type = deviceTypeRepository.findById(dto.getDeviceTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Тип устройства с id " + dto.getDeviceTypeId() + " не найден"));
    entity.setDeviceType(type);

    if (dto.getSiteId() != null) {
      Site site = siteRepository.findById(dto.getSiteId())
              .orElseThrow(() -> new ResourceNotFoundException("Площадка с id " + dto.getSiteId() + " не найдена"));
      entity.setSite(site);
    }

    return mapper.toDto(deviceRepository.save(entity));
  }

  public List<DeviceResponseDto> getAll() {
    return deviceRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
  }

  public DeviceResponseDto getById(Long id) {
    Device entity = deviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Устройство с id " + id + " не найдено"));
    return mapper.toDto(entity);
  }

  public DeviceResponseDto update(Long id, DeviceRequestDto dto) {
    Device entity = deviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Устройство с id " + id + " не найдено"));

    entity.setName(dto.getName());
    entity.setIpAddress(dto.getIpAddress());

    DeviceType type = deviceTypeRepository.findById(dto.getDeviceTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Тип устройства с id " + dto.getDeviceTypeId() + " не найден"));
    entity.setDeviceType(type);

    if (dto.getSiteId() != null) {
      Site site = siteRepository.findById(dto.getSiteId())
              .orElseThrow(() -> new ResourceNotFoundException("Площадка с id " + dto.getSiteId() + " не найдена"));
      entity.setSite(site);
    } else {
      entity.setSite(null);
    }

    return mapper.toDto(deviceRepository.save(entity));
  }

  public void delete(Long id) {
    if (!deviceRepository.existsById(id)) {
      throw new ResourceNotFoundException("Устройство с id " + id + " не найдено");
    }
    deviceRepository.deleteById(id);
  }
}