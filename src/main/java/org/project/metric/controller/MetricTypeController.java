package org.project.metric.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.metric.dto.MetricTypeRequestDto;
import org.project.metric.dto.MetricTypeResponseDto;
import org.project.metric.service.MetricTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metric-types")
@RequiredArgsConstructor
@Tag(name = "Metric Types", description = "Справочник метрик")
public class MetricTypeController {

  private final MetricTypeService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MetricTypeResponseDto create(@Valid @RequestBody MetricTypeRequestDto dto) {
    return service.create(dto);
  }

  @GetMapping
  public List<MetricTypeResponseDto> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public MetricTypeResponseDto getById(@PathVariable Long id) {
    return service.getById(id);
  }

  @PutMapping("/{id}")
  public MetricTypeResponseDto update(@PathVariable Long id, @Valid @RequestBody MetricTypeRequestDto dto) {
    return service.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}