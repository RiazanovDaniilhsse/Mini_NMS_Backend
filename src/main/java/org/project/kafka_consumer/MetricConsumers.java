package org.project.kafka_consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.metric.dto.event.BooleanMetricEventDto;
import org.project.metric.dto.event.MetricEventDto;
import org.project.metric.dto.event.NumericMetricEventDto;
import org.project.metric.dto.event.StringMetricEventDto;
import org.project.metric.service.MetricService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MetricConsumers {

  private final MetricService metricService;

  @KafkaListener(topics = "nms.metrics.numeric", groupId = "nms-backend-group")
  public void consumeNumeric(MetricEventDto event) {
    log.info("Received numeric event from Kafka: device={}, metric={}",
            event.getDeviceId(), event.getMetricTypeCode());
    NumericMetricEventDto dto = new NumericMetricEventDto();
    dto.setDeviceId(event.getDeviceId());
    dto.setMetricTypeCode(event.getMetricTypeCode());
    dto.setValue(event.getValue());
    dto.setTimestamp(event.getTimestamp());
    dto.setPollStatus(event.getPollStatus());
    metricService.saveNumeric(dto);
  }

  @KafkaListener(topics = "nms.metrics.boolean", groupId = "nms-backend-group")
  public void consumeBoolean(MetricEventDto event) {
    log.info("Received boolean event from Kafka: device={}, metric={}",
            event.getDeviceId(), event.getMetricTypeCode());
    BooleanMetricEventDto dto = new BooleanMetricEventDto();
    dto.setDeviceId(event.getDeviceId());
    dto.setMetricTypeCode(event.getMetricTypeCode());
    dto.setValue(event.getBooleanValue());
    dto.setTimestamp(event.getTimestamp());
    dto.setPollStatus(event.getPollStatus());
    metricService.saveBoolean(dto);
  }

  @KafkaListener(topics = "nms.metrics.string", groupId = "nms-backend-group")
  public void consumeString(MetricEventDto event) {
    log.info("Received string event from Kafka: device={}, metric={}",
            event.getDeviceId(), event.getMetricTypeCode());
    StringMetricEventDto dto = new StringMetricEventDto();
    dto.setDeviceId(event.getDeviceId());
    dto.setMetricTypeCode(event.getMetricTypeCode());
    dto.setValue(event.getStringValue());
    dto.setTimestamp(event.getTimestamp());
    dto.setPollStatus(event.getPollStatus());
    metricService.saveString(dto);
  }
}