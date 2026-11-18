CREATE TABLE device_timeouts
(
    id        BIGSERIAL PRIMARY KEY,
    device_id BIGINT      NOT NULL REFERENCES devices (id) ON DELETE CASCADE,
    timestamp TIMESTAMPTZ NOT NULL,
    CONSTRAINT uq_device_timeout_time UNIQUE (device_id, timestamp)
);
CREATE INDEX idx_device_timeouts_history ON device_timeouts (device_id, timestamp DESC);