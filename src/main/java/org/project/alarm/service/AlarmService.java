package org.project.alarm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.alarm.model.Alarm;
import org.project.alarm.repository.AlarmRepository;
import org.project.catalog.model.Device;
import org.project.enums.AlarmStatus;
import org.project.exception.exceptions.BadRequestException;
import org.project.exception.exceptions.ResourceNotFoundException;
import org.project.metric.model.MetricType;
import org.project.threshold.model.ThresholdRule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {

  private final AlarmRepository alarmRepository;

  // 1. Открытие аларма (вызывается из ThresholdEngineService)
  @Transactional
  public Alarm openAlarm(Device device, MetricType metricType, ThresholdRule rule,
          String snapshot) {
    var existing = alarmRepository.findByDeviceIdAndRuleIdAndStatusIn(
            device.getId(), rule.getId(), List.of(AlarmStatus.OPEN, AlarmStatus.ACK));
    if (!existing.isEmpty()) {
      log.info("Alarm already exists for device {} and rule {}", device.getId(), rule.getId());
      return existing.get(0);
    }
    Alarm alarm = new Alarm();
    alarm.setDevice(device);
    alarm.setMetricType(metricType);
    alarm.setRule(rule);
    alarm.setSeverity(rule.getSeverity());
    alarm.setStatus(AlarmStatus.OPEN);
    alarm.setMessage("Threshold exceeded: " + snapshot);
    alarm.setThresholdSnapshot(snapshot);
    alarm.setTriggeredAt(ZonedDateTime.now());
    log.warn("ALARM OPENED: device={}, rule={}", device.getId(), rule.getId());
    return alarmRepository.save(alarm);
  }

  // 2. Подтверждение (ACK) — инженер нажал кнопку
  @Transactional
  public Alarm acknowledge(Long alarmId) {
    Alarm alarm = findById(alarmId);
    if (alarm.getStatus() != AlarmStatus.OPEN) {
      throw new BadRequestException("Only OPEN alarms can be acknowledged");
    }
    alarm.setStatus(AlarmStatus.ACK);
    alarm.setAckedAt(ZonedDateTime.now());
    log.info("ALARM ACK: id={}", alarmId);
    return alarmRepository.save(alarm);
  }

  // 3. Решение (RESOLVED) — условие исчезло (автоматически или вручную)
  @Transactional
  public Alarm resolve(Long alarmId) {
    Alarm alarm = findById(alarmId);
    if (alarm.getStatus() != AlarmStatus.OPEN && alarm.getStatus() != AlarmStatus.ACK) {
      throw new BadRequestException("Only OPEN or ACK alarms can be resolved");
    }
    alarm.setStatus(AlarmStatus.RESOLVED);
    alarm.setResolvedAt(ZonedDateTime.now());
    log.info("ALARM RESOLVED: id={}", alarmId);
    return alarmRepository.save(alarm);
  }

  // 4. Закрытие (CLOSED) — инженер закрыл
  @Transactional
  public Alarm close(Long alarmId) {
    Alarm alarm = findById(alarmId);
    if (alarm.getStatus() != AlarmStatus.RESOLVED) {
      throw new BadRequestException("Only RESOLVED alarms can be closed");
    }
    alarm.setStatus(AlarmStatus.CLOSED);
    alarm.setClosedAt(ZonedDateTime.now());
    log.info("ALARM CLOSED: id={}", alarmId);
    return alarmRepository.save(alarm);
  }

  // 5. Автоматическое закрытие по правилу (когда метрика вернулась в норму)
  @Transactional
  public void resolveByRule(Long deviceId, Long ruleId) {
    var alarms = alarmRepository.findByDeviceIdAndRuleIdAndStatusIn(
            deviceId, ruleId, List.of(AlarmStatus.OPEN, AlarmStatus.ACK));
    for (Alarm alarm : alarms) {
      alarm.setStatus(AlarmStatus.RESOLVED);
      alarm.setResolvedAt(ZonedDateTime.now());
      alarmRepository.save(alarm);
      log.info("ALARM AUTO-RESOLVED: id={}", alarm.getId());
    }
  }

  private Alarm findById(Long id) {
    return alarmRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Alarm not found: " + id));
  }
  public List<Alarm> getAll(){
    return alarmRepository.findAll();
  }
  public List<Alarm> getActive(){
    return alarmRepository.findAlarmByStatusIn(List.of(AlarmStatus.OPEN, AlarmStatus.ACK, AlarmStatus.RESOLVED));
  }
}