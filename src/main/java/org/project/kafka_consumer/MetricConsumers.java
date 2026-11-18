package org.project.kafka_consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.metric.dto.event.BooleanMetricEventDto;
import org.project.metric.dto.event.MetricEventDto;
import org.project.metric.dto.event.NumericMetricEventDto;
import org.project.metric.dto.event.StringMetricEventDto;
import org.project.metric.dto.event.TimeoutMetricEventDto;
import org.project.metric.service.MetricService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MetricConsumers {

  private final MetricService metricService;

  @KafkaListener(topics = "nms.metrics.numeric", groupId = "nms-backend-group")
  public void consumeNumeric(NumericMetricEventDto event) {
    log.info("Received numeric event from Kafka: device={}, metric={}",
            event.getDeviceId(), event.getMetricTypeCode());
    metricService.saveNumeric(event);
  }

  @KafkaListener(topics = "nms.metrics.boolean", groupId = "nms-backend-group")
  public void consumeBoolean(BooleanMetricEventDto event) {
    log.info("Received boolean event from Kafka: device={}, metric={}",
            event.getDeviceId(), event.getMetricTypeCode());
    metricService.saveBoolean(event);
  }

  @KafkaListener(topics = "nms.metrics.string", groupId = "nms-backend-group")
  public void consumeString(StringMetricEventDto event) {
    log.info("Received string event from Kafka: device={}, metric={}",
            event.getDeviceId(), event.getMetricTypeCode());
    metricService.saveString(event);
  }

  @KafkaListener(topics = "nms.metrics.timeout", groupId = "nms-backend-group")
  public void consumeTimeout(TimeoutMetricEventDto event) {
    log.warn("!!! TIMEOUT for device {} at {} !!!", event.getDeviceId(), event.getTimestamp());
    metricService.saveTimeout(event);
  }
}
