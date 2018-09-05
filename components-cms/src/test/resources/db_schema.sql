DROP TABLE IF EXISTS account;

CREATE TABLE account (
    id VARCHAR(36) NOT NULL,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    creation_date DATETIME NOT NULL
);

ALTER TABLE account ADD PRIMARY KEY (id);
ALTER TABLE account ADD CONSTRAINT uk_account_username UNIQUE (username);
ALTER TABLE account ADD CONSTRAINT uk_account_email UNIQUE (email);

DROP TABLE IF EXISTS permission;

CREATE TABLE permission (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    creation_date DATETIME NOT NULL
);

ALTER TABLE permission ADD PRIMARY KEY (id);

DROP TABLE IF EXISTS account_permission;

CREATE TABLE account_permission (
    account_id VARCHAR(36) NOT NULL,
    permission_id VARCHAR(255) NOT NULL
);

ALTER TABLE account_permission ADD PRIMARY KEY (account_id, permission_id);

DROP TABLE IF EXISTS news;

CREATE TABLE news(
    id VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    author_id VARCHAR(36) NOT NULL,
    title VARCHAR(255),
    url_friendly_title VARCHAR(255),
    description VARCHAR(500),
    content VARCHAR(5000),
    rendered_content VARCHAR(5000),
    source VARCHAR(500),
    publication_date DATETIME,
    status VARCHAR(128) NOT NULL
);

ALTER TABLE news ADD PRIMARY KEY (id);

DROP TABLE IF EXISTS event_log;

CREATE TABLE event_log (
    id VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    description VARCHAR(255) NOT NULL
);

ALTER TABLE event_log ADD PRIMARY KEY (id);

DROP TABLE IF EXISTS country;

CREATE TABLE country(
    code VARCHAR(2) NOT NULL,
    name VARCHAR(255) NOT NULL,
    creation_date DATETIME NOT NULL
);

ALTER TABLE country ADD PRIMARY KEY (code);

