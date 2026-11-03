package org.project.metric.repository;

import org.project.catalog.model.Device;
import org.project.catalog.repository.DeviceRepository;
import org.project.catalog.model.DeviceType;
import org.project.catalog.repository.DeviceTypeRepository;
import org.project.enums.PollStatus;
import org.project.enums.ValueType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.metric.model.MetricNumericValue;
import org.project.metric.model.MetricType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class MetricNumericValueRepositoryTest {

  @Autowired
  private MetricNumericValueRepository numericRepository;
  @Autowired
  private MetricTypeRepository metricTypeRepository;
  @Autowired
  private DeviceRepository deviceRepository;
  @Autowired
  private DeviceTypeRepository deviceTypeRepository;

  private Device device;
  private MetricType metricType;

  @BeforeEach
  void setUp() {
    // Создаем тип устройства
    DeviceType deviceType = new DeviceType();
    deviceType.setCode("TEST_ROUTER");
    deviceType.setName("Test Router");
    deviceType = deviceTypeRepository.save(deviceType);

    // Создаем устройство
    device = new Device();
    device.setName("test-device");
    device.setDeviceType(deviceType);
    device = deviceRepository.save(device);

    // Создаем метрику
    metricType = new MetricType();
    metricType.setCode("TEST_CPU");
    metricType.setName("Test CPU");
    metricType.setValueType(ValueType.NUMBER);
    metricType = metricTypeRepository.save(metricType);
  }

  @Test
  void shouldSaveAndFindNumericMetric() {
    // Given
    MetricNumericValue metric = new MetricNumericValue();
    metric.setDevice(device);
    metric.setMetricType(metricType);
    metric.setValue(new BigDecimal("85.5"));
    metric.setTimestamp(ZonedDateTime.now());
    metric.setPollStatus(PollStatus.SUCCESS);

    // When
    MetricNumericValue saved = numericRepository.save(metric);

    // Then
    assertThat(saved.getId()).isNotNull();
    assertThat(numericRepository.findById(saved.getId())).isPresent();
  }

  @Test
  void shouldThrowException_WhenDuplicateTimestamp() {
    // Given
    ZonedDateTime timestamp = ZonedDateTime.now();
    MetricNumericValue metric1 = new MetricNumericValue();
    metric1.setDevice(device);
    metric1.setMetricType(metricType);
    metric1.setValue(new BigDecimal("85.5"));
    metric1.setTimestamp(timestamp);
    metric1.setPollStatus(PollStatus.SUCCESS);
    numericRepository.saveAndFlush(metric1);

    // When & Then (пытаемся сохранить с тем же timestamp)
    MetricNumericValue metric2 = new MetricNumericValue();
    metric2.setDevice(device);
    metric2.setMetricType(metricType);
    metric2.setValue(new BigDecimal("90.0"));
    metric2.setTimestamp(timestamp);
    metric2.setPollStatus(PollStatus.SUCCESS);

    assertThatThrownBy(() -> numericRepository.saveAndFlush(metric2))
            .isInstanceOf(DataIntegrityViolationException.class);
  }
}