CREATE TABLE uuid_persistent_resource(
    uuid VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    label VARCHAR(255) NOT NULL
);

ALTER TABLE uuid_persistent_resource ADD PRIMARY KEY (uuid);

CREATE TABLE country(
    code VARCHAR(2) NOT NULL,
    name VARCHAR(255) NOT NULL,
    creation_date DATETIME NOT NULL
);

ALTER TABLE country ADD PRIMARY KEY (code);

CREATE TABLE event_log (
    id VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    description VARCHAR(255) NOT NULL
);

ALTER TABLE event_log ADD PRIMARY KEY (id);