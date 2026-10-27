package org.project.metric.service;

import lombok.RequiredArgsConstructor;
import org.project.exception.exceptions.ResourceNotFoundException;
import org.project.metric.dto.MetricTypeRequestDto;
import org.project.metric.dto.MetricTypeResponseDto;
import org.project.metric.mapper.MetricTypeMapper;
import org.project.metric.model.MetricType;
import org.project.metric.repository.MetricTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetricTypeService {

  private final MetricTypeRepository repository;
  private final MetricTypeMapper mapper;

  public MetricTypeResponseDto create(MetricTypeRequestDto dto) {
    return mapper.toDto(repository.save(mapper.toEntity(dto)));
  }

  public List<MetricTypeResponseDto> getAll() {
    return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
  }

  public MetricTypeResponseDto getById(Long id) {
    return repository.findById(id)
            .map(mapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Метрика с id " + id + " не найдена"));
  }

  public MetricTypeResponseDto update(Long id, MetricTypeRequestDto dto) {
    MetricType entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Метрика с id " + id + " не найдена"));

    entity.setCode(dto.getCode());
    entity.setName(dto.getName());
    entity.setUnit(dto.getUnit());
    entity.setValueType(dto.getValueType());
    return mapper.toDto(repository.save(entity));
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Метрика с id " + id + " не найдена");
    }
    repository.deleteById(id);
  }
}