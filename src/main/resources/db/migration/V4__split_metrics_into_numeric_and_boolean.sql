CREATE TABLE metrics_numeric
(
    id             BIGSERIAL PRIMARY KEY,
    device_id      BIGINT         NOT NULL REFERENCES devices (id) ON DELETE CASCADE,
    metric_type_id BIGINT         NOT NULL REFERENCES metric_types (id),
    value          NUMERIC(15, 4) NOT NULL,
    poll_status    VARCHAR(20)    NOT NULL,
    timestamp      TIMESTAMPTZ    NOT NULL,
    CONSTRAINT uq_metric_num_time UNIQUE (device_id, metric_type_id, timestamp)
);
CREATE INDEX idx_metrics_num_history ON metrics_numeric (device_id, metric_type_id, timestamp DESC);

CREATE TABLE metrics_boolean
(
    id             BIGSERIAL PRIMARY KEY,
    device_id      BIGINT      NOT NULL REFERENCES devices (id) ON DELETE CASCADE,
    metric_type_id BIGINT      NOT NULL REFERENCES metric_types (id),
    value          BOOLEAN     NOT NULL,
    poll_status    VARCHAR(20) NOT NULL,
    timestamp      TIMESTAMPTZ NOT NULL,
    CONSTRAINT uq_metric_bool_time UNIQUE (device_id, metric_type_id, timestamp)
);
CREATE INDEX idx_metrics_bool_history ON metrics_boolean (device_id, metric_type_id, timestamp DESC);

INSERT INTO metrics_numeric (device_id, metric_type_id, value, poll_status, timestamp)
SELECT m.device_id, m.metric_type_id, m.value, m.poll_status, m.timestamp
FROM metrics m
         JOIN metric_types mt ON m.metric_type_id = mt.id
WHERE mt.value_type = 'NUMBER'
  AND m.value IS NOT NULL;
INSERT INTO metrics_boolean (device_id, metric_type_id, value, poll_status, timestamp)
SELECT m.device_id, m.metric_type_id, (m.value = 1), m.poll_status, m.timestamp
FROM metrics m
         JOIN metric_types mt ON m.metric_type_id = mt.id
WHERE mt.value_type = 'BOOLEAN'
  AND m.value IS NOT NULL;

DROP TABLE metrics;