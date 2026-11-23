package org.project.emulator;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@RestController
@RequestMapping("/devices")
public class EmulatorController {

  private int value = 0;

  @GetMapping("/{id}/metrics")
  public List<Map<String, Object>> getMetrics(
          @PathVariable Long id,
          @RequestParam(required = false) Set<String> metric) {
    List<Map<String, Object>> allMetrics = new ArrayList<>();
    // Числовая метрика
    allMetrics.add(Map.of(
            "type", "DOUBLE",
            "metric", "CPU_LOAD",
            "value", value,
            "timestamp", Instant.now().toString()
    ));
    // Булева метрика
    allMetrics.add(Map.of(
            "type", "BOOLEAN",
            "metric", "DOOR_OPEN",
            "value", true,
            "timestamp", Instant.now().toString()
    ));
    // Строковая метрика
    allMetrics.add(Map.of(
            "type", "STRING",
            "metric", "UPS_MODE",
            "value", "ON_LINE",
            "timestamp", Instant.now().toString()
    ));
    if (metric != null && !metric.isEmpty()) {
      return allMetrics.stream()
              .filter(m -> metric.contains(m.get("metric")))
              .toList();
    }
    return allMetrics;
  }

  @PatchMapping("/{val}")
  public void changeValue(@PathVariable Integer val) {
    value = val;
  }

}