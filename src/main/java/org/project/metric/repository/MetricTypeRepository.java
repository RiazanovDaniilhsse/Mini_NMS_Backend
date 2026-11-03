package org.project.metric.repository;

import java.util.Optional;
import org.project.metric.dto.MetricEventDto;
import org.project.metric.model.MetricType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricTypeRepository extends JpaRepository<MetricType, Long> {

  Optional<MetricType> findByCode(String code);
}
