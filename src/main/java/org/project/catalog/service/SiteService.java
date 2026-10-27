package org.project.catalog.service;

import lombok.RequiredArgsConstructor;
import org.project.catalog.dto.SiteRequestDto;
import org.project.catalog.dto.SiteResponseDto;
import org.project.catalog.mapper.SiteMapper;
import org.project.catalog.model.Site;
import org.project.catalog.repository.SiteRepository;
import org.project.exception.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteService {

  private final SiteRepository siteRepository;
  private final SiteMapper siteMapper;

  public SiteResponseDto create(SiteRequestDto dto) {
    Site site = siteMapper.toEntity(dto);
    Site saved = siteRepository.save(site);
    return siteMapper.toDto(saved);
  }

  public List<SiteResponseDto> getAll() {
    return siteRepository.findAll().stream()
            .map(siteMapper::toDto)
            .collect(Collectors.toList());
  }

  public SiteResponseDto getById(Long id) {
    Site site = siteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Площадка с id " + id + " не найдена"));
    return siteMapper.toDto(site);
  }

  public SiteResponseDto update(Long id, SiteRequestDto dto) {
    Site site = siteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Площадка с id " + id + " не найдена"));

    site.setName(dto.getName());
    site.setAddress(dto.getAddress());
    site.setLatitude(dto.getLatitude());
    site.setLongitude(dto.getLongitude());
    return siteMapper.toDto(siteRepository.save(site));
  }

  public void delete(Long id) {
    if (!siteRepository.existsById(id)) {
      throw new ResourceNotFoundException("Площадка с id " + id + " не найдена");
    }
    siteRepository.deleteById(id);
  }
}