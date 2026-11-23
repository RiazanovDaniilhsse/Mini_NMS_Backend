ALTER TABLE devices ADD COLUMN port INTEGER;
ALTER TABLE devices ADD CONSTRAINT chk_devices_port CHECK (port IS NULL OR (port >= 1 AND port <= 65535));