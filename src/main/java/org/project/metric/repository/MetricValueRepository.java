package org.project.metric.repository;

import org.project.metric.model.MetricValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricValueRepository extends JpaRepository<MetricValue, Long> {

}
