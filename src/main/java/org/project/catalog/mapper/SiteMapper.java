package org.project.catalog.mapper;


import org.project.catalog.dto.SiteRequestDto;
import org.project.catalog.dto.SiteResponseDto;
import org.project.catalog.model.Site;
import org.springframework.stereotype.Component;

@Component
public class SiteMapper {

  public Site toEntity(SiteRequestDto dto) {
    Site site = new Site();
    site.setName(dto.getName());
    site.setAddress(dto.getAddress());
    site.setLatitude(dto.getLatitude());
    site.setLongitude(dto.getLongitude());
    return site;
  }

  public SiteResponseDto toDto(Site site) {
    SiteResponseDto dto = new SiteResponseDto();
    dto.setId(site.getId());
    dto.setName(site.getName());
    dto.setAddress(site.getAddress());
    dto.setLatitude(site.getLatitude());
    dto.setLongitude(site.getLongitude());
    return dto;
  }
}
