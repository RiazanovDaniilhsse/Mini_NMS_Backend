package org.project.threshold.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.alarm.service.AlarmService;
import org.project.catalog.model.Device;
import org.project.metric.model.MetricType;
import org.project.threshold.model.ThresholdRule;
import org.project.threshold.repository.ThresholdRuleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThresholdEngineService {

  private final ThresholdRuleRepository ruleRepository;
  private final AlarmService alarmService;

  public void evaluate(Device device, MetricType metricType, BigDecimal numericValue,
          Boolean boolValue, String stringValue) {
    List<ThresholdRule> rules = ruleRepository.findByMetricTypeIdAndEnabledTrue(metricType.getId());
    for (ThresholdRule rule : rules) {
      boolean violated = checkViolation(rule, numericValue, boolValue, stringValue);
      String thresholdValue;
      if (violated) {
        switch (metricType.getValueType()) {
          case NUMBER -> thresholdValue = rule.getNumericValue().toEngineeringString();
          case BOOLEAN -> thresholdValue = rule.getBooleanValue().toString();
          case STRING -> thresholdValue = rule.getStringValues().toString();
          default -> thresholdValue = "";
        }
        String snapshot = String.format("%s %s %s", metricType.getCode(), rule.getComparator(),
                thresholdValue);
        alarmService.openAlarm(
                device,
                metricType,
                rule,
                snapshot
        );
      } else {
        alarmService.resolveByRule(device.getId(), rule.getId());
      }
    }
  }

  private boolean checkViolation(ThresholdRule rule, BigDecimal num, Boolean bool, String str) {
    if (num != null && rule.getNumericValue() != null) {
      int cmp = num.compareTo(rule.getNumericValue());
      return switch (rule.getComparator()) {
        case GT -> cmp > 0;
        case LT -> cmp < 0;
        case GTE -> cmp >= 0;
        case LTE -> cmp <= 0;
        case EQ -> cmp == 0;
      };
    }
    if (bool != null && rule.getBooleanValue() != null) {
      return bool == rule.getBooleanValue();
    }
    if (str != null && rule.getStringValues() != null) {
      return rule.getStringValues().contains(str);
    }
    return false;
  }
}