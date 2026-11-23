-- Для устройств
ALTER TABLE devices ADD COLUMN snmp_port INTEGER;
ALTER TABLE devices ADD COLUMN community VARCHAR(50) DEFAULT 'public';
ALTER TABLE devices DROP COLUMN port;
-- Для метрик
ALTER TABLE metric_types ADD COLUMN min_value NUMERIC(15,4);
ALTER TABLE metric_types ADD COLUMN max_value NUMERIC(15,4);
ALTER TABLE metric_types ADD COLUMN normal_low NUMERIC(15,4);
ALTER TABLE metric_types ADD COLUMN normal_high NUMERIC(15,4);
ALTER TABLE metric_types ADD COLUMN step NUMERIC(15,4) DEFAULT 1;
ALTER TABLE metric_types ADD COLUMN snmp_type VARCHAR(20) DEFAULT 'gauge';
ALTER TABLE metric_types ADD COLUMN kind VARCHAR(20) DEFAULT 'numeric';
ALTER TABLE metric_types ADD COLUMN mode VARCHAR(20);
ALTER TABLE metric_types ADD COLUMN oid VARCHAR(100);
ALTER TABLE metric_types ADD COLUMN string_values TEXT;
ALTER TABLE metric_types ADD COLUMN static_value TEXT;