CREATE TABLE uuid_entity(
    uuid VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    label VARCHAR(255) NOT NULL
);

ALTER TABLE uuid_entity ADD PRIMARY KEY (uuid);

CREATE TABLE country(
    uuid VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    code VARCHAR(2) NOT NULL,
    name VARCHAR(255) NOT NULL
);

ALTER TABLE country ADD PRIMARY KEY (uuid);
ALTER TABLE country ADD UNIQUE KEY uk_country_code(code);

CREATE TABLE event_log (
    uuid VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    description_key VARCHAR(255) NOT NULL
);

ALTER TABLE event_log ADD PRIMARY KEY (uuid);