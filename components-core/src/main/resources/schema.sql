CREATE TABLE event_log (
  id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  uuid VARCHAR(38) NOT NULL,
  creation_date DATETIME NOT NULL,
  description_key VARCHAR(255) NOT NULL
);

ALTER TABLE event_log ADD UNIQUE KEY uk_event_log_uuid (uuid);