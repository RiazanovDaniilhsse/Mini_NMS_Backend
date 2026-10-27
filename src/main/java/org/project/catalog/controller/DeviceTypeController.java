package org.project.catalog.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.catalog.dto.DeviceTypeRequestDto;
import org.project.catalog.dto.DeviceTypeResponseDto;
import org.project.catalog.service.DeviceTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device-types")
@RequiredArgsConstructor
@Tag(name = "Device Types", description = "Управление типами устройств")
public class DeviceTypeController {

  private final DeviceTypeService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public DeviceTypeResponseDto create(@Valid @RequestBody DeviceTypeRequestDto dto) {
    return service.create(dto);
  }

  @GetMapping
  public List<DeviceTypeResponseDto> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public DeviceTypeResponseDto getById(@PathVariable Long id) {
    return service.getById(id);
  }

  @PutMapping("/{id}")
  public DeviceTypeResponseDto update(@PathVariable Long id, @Valid @RequestBody DeviceTypeRequestDto dto) {
    return service.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}