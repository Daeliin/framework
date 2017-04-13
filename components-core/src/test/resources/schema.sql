CREATE TABLE uuid_entity(
  id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  uuid VARCHAR(255) NOT NULL,
  creation_date DATETIME NOT NULL,
  label VARCHAR(255) NOT NULL
);

ALTER TABLE uuid_entity ADD UNIQUE KEY uk_uuid_entity_uuid (uuid);

CREATE TABLE country(
  id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  uuid VARCHAR(255) NOT NULL,
  creation_date DATETIME NOT NULL,
  code VARCHAR(2) NOT NULL,
  name VARCHAR(255) NOT NULL
);

ALTER TABLE country ADD UNIQUE KEY uk_country_uuid (uuid);

CREATE TABLE event_log (
  id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  uuid VARCHAR(255) NOT NULL,
  creation_date DATETIME NOT NULL,
  description_key VARCHAR(255) NOT NULL
);

ALTER TABLE event_log ADD UNIQUE KEY uk_event_log_uuid (uuid);