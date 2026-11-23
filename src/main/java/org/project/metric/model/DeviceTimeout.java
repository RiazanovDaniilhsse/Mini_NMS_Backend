package org.project.metric.model;

import jakarta.persistence.*;
import lombok.Data;
import org.project.catalog.model.Device;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(
        name = "device_timeouts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"device_id", "timestamp"})
)
public class DeviceTimeout {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "device_id")
  private Device device;

  @Column(name = "timestamp", nullable = false)
  private ZonedDateTime timestamp;
}