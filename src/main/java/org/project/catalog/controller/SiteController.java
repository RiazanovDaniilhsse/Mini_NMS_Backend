package org.project.catalog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.catalog.dto.SiteRequestDto;
import org.project.catalog.dto.SiteResponseDto;
import org.project.catalog.service.SiteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sites")
@RequiredArgsConstructor
@Tag(name = "Sites", description = "Управление площадками")
public class SiteController {

  private final SiteService siteService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Создать новую площадку")
  public SiteResponseDto create(@Valid @RequestBody SiteRequestDto dto) {
    return siteService.create(dto);
  }

  @GetMapping
  @Operation(summary = "Получить список всех площадок")
  public List<SiteResponseDto> getAll() {
    return siteService.getAll();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Получить площадку по ID")
  public SiteResponseDto getById(@PathVariable Long id) {
    return siteService.getById(id);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Обновить площадку")
  public SiteResponseDto update(@PathVariable Long id, @Valid @RequestBody SiteRequestDto dto) {
    return siteService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Удалить площадку")
  public void delete(@PathVariable Long id) {
    siteService.delete(id);
  }
}