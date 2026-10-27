package org.project.threshold.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.threshold.dto.ThresholdRuleRequestDto;
import org.project.threshold.dto.ThresholdRuleResponseDto;
import org.project.threshold.service.ThresholdRuleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/threshold-rules")
@RequiredArgsConstructor
@Tag(name = "Threshold Rules", description = "Управление пороговыми правилами")
public class ThresholdRuleController {

  private final ThresholdRuleService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ThresholdRuleResponseDto create(@Valid @RequestBody ThresholdRuleRequestDto dto) {
    return service.create(dto);
  }

  @GetMapping
  public List<ThresholdRuleResponseDto> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public ThresholdRuleResponseDto getById(@PathVariable Long id) {
    return service.getById(id);
  }

  @PutMapping("/{id}")
  public ThresholdRuleResponseDto update(@PathVariable Long id, @Valid @RequestBody ThresholdRuleRequestDto dto) {
    return service.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}