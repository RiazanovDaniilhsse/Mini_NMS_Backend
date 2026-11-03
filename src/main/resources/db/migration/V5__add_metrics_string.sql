CREATE TABLE metrics_string (
                                id BIGSERIAL PRIMARY KEY,
                                device_id BIGINT NOT NULL REFERENCES devices(id) ON DELETE CASCADE,
                                metric_type_id BIGINT NOT NULL REFERENCES metric_types(id),
                                value VARCHAR(255) NOT NULL,
                                poll_status VARCHAR(20) NOT NULL,
                                timestamp TIMESTAMPTZ NOT NULL,
                                CONSTRAINT uq_metric_str_time UNIQUE (device_id, metric_type_id, timestamp)
);
CREATE INDEX idx_metrics_str_history ON metrics_string (device_id, metric_type_id, timestamp DESC);