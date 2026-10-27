package org.project.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SiteRequestDto {

  @NotBlank(message = "Название площадки не может быть пустым")
  @Size(max = 255, message = "Название не должно превышать 255 символов")
  private String name;

  @Size(max = 500, message = "Адрес не должен превышать 500 символов")
  private String address;

  private Double latitude;
  private Double longitude;
}