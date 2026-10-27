package org.project.catalog.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "devices")
public class Device {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String ipAddress;

  @ManyToOne
  @JoinColumn(name = "site_id")
  private Site site;

  @ManyToOne(optional = false)
  @JoinColumn(name = "device_type_id")
  private DeviceType deviceType;
}