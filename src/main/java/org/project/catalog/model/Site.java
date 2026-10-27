package org.project.catalog.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "sites")
public class Site {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;
  private String address;
  private Double latitude;
  private Double longitude;
}
