package org.project.catalog.dto;

import lombok.Data;

@Data
public class SiteResponseDto {

  private Long id;
  private String name;
  private String address;
  private Double latitude;
  private Double longitude;
}