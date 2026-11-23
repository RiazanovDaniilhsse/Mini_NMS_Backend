package org.project.alarm.alarm;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.alarm.model.Alarm;
import org.project.alarm.service.AlarmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
@Tag(name = "Alarms", description = "Управление алармами")
public class AlarmController {

  private final AlarmService alarmService;

  @GetMapping
  public List<Alarm> getAll() {
    return alarmService.getAll();
  }

  @GetMapping("/active")
  public List<Alarm> getActive() {
    return alarmService.getActive();
  }

  @PostMapping("/{id}/ack")
  public Alarm ack(@PathVariable Long id) {
    return alarmService.acknowledge(id);
  }

  @PostMapping("/{id}/resolve")
  public Alarm resolve(@PathVariable Long id) {
    return alarmService.resolve(id);
  }

  @PostMapping("/{id}/close")
  public Alarm close(@PathVariable Long id) {
    return alarmService.close(id);
  }
}