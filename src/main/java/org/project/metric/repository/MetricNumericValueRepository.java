package org.project.metric.repository;

import org.project.metric.model.MetricNumericValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricNumericValueRepository extends JpaRepository<MetricNumericValue, Long> {

}
