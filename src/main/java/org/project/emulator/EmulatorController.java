package org.project.emulator;

import java.time.Instant;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/devices")
public class EmulatorController {

  private final Random random = new Random();

  @GetMapping("/{id}/metrics")
  public List<Map<String, Object>> getMetrics(
          @PathVariable Long id,
          @RequestParam(required = false) Set<String> metric) {
    List<Map<String, Object>> allMetrics = new ArrayList<>();
    // Числовая метрика
    allMetrics.add(Map.of(
            "type", "DOUBLE", // <-- Вот это поле!
            "metric", "CPU_LOAD",
            "value", 85.5,
            "timestamp", Instant.now().toString()
    ));
    // Булева метрика
    allMetrics.add(Map.of(
            "type", "BOOLEAN", // <-- Вот это поле!
            "metric", "DOOR_OPEN",
            "value", true,
            "timestamp", Instant.now().toString()
    ));
    // Строковая метрика
    allMetrics.add(Map.of(
            "type", "STRING", // <-- Вот это поле!
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
}