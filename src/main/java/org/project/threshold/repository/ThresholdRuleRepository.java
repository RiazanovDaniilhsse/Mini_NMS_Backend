package org.project.threshold.repository;

import java.util.List;
import org.project.threshold.model.ThresholdRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThresholdRuleRepository extends JpaRepository<ThresholdRule, Long> {

  List<ThresholdRule> findByMetricTypeIdAndEnabledTrue(Long id);
}
