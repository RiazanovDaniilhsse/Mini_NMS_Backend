package org.project.catalog.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.catalog.dto.DeviceRequestDto;
import org.project.catalog.dto.DeviceResponseDto;
import org.project.catalog.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@Tag(name = "Devices", description = "Управление устройствами")
public class DeviceController {

  private final DeviceService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public DeviceResponseDto create(@Valid @RequestBody DeviceRequestDto dto) {
    return service.create(dto);
  }

  @GetMapping
  public List<DeviceResponseDto> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public DeviceResponseDto getById(@PathVariable Long id) {
    return service.getById(id);
  }

  @PutMapping("/{id}")
  public DeviceResponseDto update(@PathVariable Long id, @Valid @RequestBody DeviceRequestDto dto) {
    return service.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}