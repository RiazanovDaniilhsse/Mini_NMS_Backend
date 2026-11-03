package org.project.kafka_consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.exception.exceptions.ResourceNotFoundException;
import org.project.metric.dto.MetricEventDto;
import org.project.metric.service.MetricService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MetricConsumer {

  private final MetricService metricService;

  @KafkaListener(topics = "nms.metrics", groupId = "nms-backend-group")
  public void consumeMetric(MetricEventDto event) {
    log.info("Received event from Kafka: device={}, metric={}",
            event.getDeviceId(), event.getMetricTypeCode());
    try {
      metricService.saveFromEvent(event);
    } catch (ResourceNotFoundException e) {
      log.error("Cannot process metric: {}", e.getMessage());
    } catch (Exception e) {
      log.error("Unexpected error while processing metric", e);
    }
  }
}