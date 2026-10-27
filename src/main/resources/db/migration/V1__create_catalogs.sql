CREATE TABLE sites (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE,
                       address VARCHAR(500),
                       latitude DOUBLE PRECISION,
                       longitude DOUBLE PRECISION
);

CREATE TABLE metric_types (
                              id BIGSERIAL PRIMARY KEY,
                              code VARCHAR(50) NOT NULL UNIQUE,
                              name VARCHAR(255) NOT NULL,
                              unit VARCHAR(20),
                              value_type VARCHAR(20) NOT NULL
);

CREATE TABLE device_types (
                              id BIGSERIAL PRIMARY KEY,
                              code VARCHAR(50) NOT NULL UNIQUE,
                              name VARCHAR(255) NOT NULL,
                              description TEXT
);

CREATE TABLE device_type_metric_type (
                                         device_type_id BIGINT NOT NULL REFERENCES device_types(id) ON DELETE CASCADE,
                                         metric_type_id BIGINT NOT NULL REFERENCES metric_types(id) ON DELETE CASCADE,
                                         PRIMARY KEY (device_type_id, metric_type_id)
);

CREATE TABLE devices (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL UNIQUE,
                         ip_address VARCHAR(45),
                         site_id BIGINT REFERENCES sites(id) ON DELETE SET NULL,
                         device_type_id BIGINT NOT NULL REFERENCES device_types(id)
);