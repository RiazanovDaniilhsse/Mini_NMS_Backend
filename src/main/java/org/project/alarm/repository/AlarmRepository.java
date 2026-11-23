package org.project.alarm.repository;
import java.util.List;
import org.project.alarm.model.Alarm;
import org.project.enums.AlarmStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository <Alarm, Long>{
  List<Alarm> findByDeviceIdAndRuleIdAndStatusIn(
          Long deviceId, Long ruleId, List<AlarmStatus> statuses);

  List<Alarm> findAlarmByStatusIn(List<AlarmStatus> open);
}
