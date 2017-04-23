
CREATE TABLE uuid_persistent_resource(
    uuid VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    label VARCHAR(255) NOT NULL
);

ALTER TABLE uuid_persistent_resource ADD PRIMARY KEY (uuid);