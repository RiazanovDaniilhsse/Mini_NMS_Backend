CREATE TABLE metrics (
                         id BIGSERIAL PRIMARY KEY,
                         device_id BIGINT NOT NULL REFERENCES devices(id) ON DELETE CASCADE,
                         metric_type_id BIGINT NOT NULL REFERENCES metric_types(id),
                         value NUMERIC(15,4),
                         poll_status VARCHAR(20) NOT NULL,
                         timestamp TIMESTAMPTZ NOT NULL,
                         CONSTRAINT uq_metric_time UNIQUE (device_id, metric_type_id, timestamp)
);
CREATE INDEX idx_metrics_history ON metrics (device_id, metric_type_id, timestamp DESC);

CREATE TABLE threshold_rules (
                                 id BIGSERIAL PRIMARY KEY,
                                 device_type_id BIGINT NOT NULL REFERENCES device_types(id),
                                 metric_type_id BIGINT NOT NULL REFERENCES metric_types(id),
                                 device_id BIGINT REFERENCES devices(id) ON DELETE CASCADE,
                                 comparator VARCHAR(10) NOT NULL,
                                 threshold_value NUMERIC(15,4) NOT NULL,
                                 duration_sec INTEGER NOT NULL DEFAULT 0,
                                 severity VARCHAR(20) NOT NULL,
                                 enabled BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE alarms (
                        id BIGSERIAL PRIMARY KEY,
                        device_id BIGINT NOT NULL REFERENCES devices(id) ON DELETE CASCADE,
                        rule_id BIGINT REFERENCES threshold_rules(id) ON DELETE SET NULL,
                        metric_type_id BIGINT REFERENCES metric_types(id),
                        severity VARCHAR(20) NOT NULL,
                        status VARCHAR(20) NOT NULL,
                        message TEXT,
                        threshold_snapshot VARCHAR(255),
                        triggered_at TIMESTAMPTZ NOT NULL,
                        acked_at TIMESTAMPTZ,
                        resolved_at TIMESTAMPTZ,
                        closed_at TIMESTAMPTZ
);