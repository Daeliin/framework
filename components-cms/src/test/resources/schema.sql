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

CREATE TABLE permission (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    creation_date DATETIME NOT NULL
);

ALTER TABLE permission ADD PRIMARY KEY (id);

CREATE TABLE account_permission (
    account_id VARCHAR(36) NOT NULL,
    permission_id VARCHAR(255) NOT NULL
);

ALTER TABLE account_permission ADD PRIMARY KEY (account_id, permission_id);
ALTER TABLE account_permission ADD CONSTRAINT fk_account_permission_account_id FOREIGN KEY (account_id) REFERENCES account (id);
ALTER TABLE account_permission ADD CONSTRAINT fk_account_permission_permission_idl FOREIGN KEY (permission_id) REFERENCES permission (id);

CREATE TABLE news(
    id VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    author_id VARCHAR(36) NOT NULL,
    title VARCHAR(255),
    url_friendly_title VARCHAR(255),
    description VARCHAR(500),
    content VARCHAR(5000),
    source VARCHAR(500),
    publication_date DATETIME,
    published BOOLEAN NOT NULL DEFAULT FALSE
);

ALTER TABLE news ADD PRIMARY KEY (id);
ALTER TABLE news ADD CONSTRAINT fk_news_account_id FOREIGN KEY (author_id) REFERENCES account (id);

CREATE TABLE event_log (
    id VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    description VARCHAR(255) NOT NULL
);

ALTER TABLE event_log ADD PRIMARY KEY (id);

CREATE TABLE country(
    code VARCHAR(2) NOT NULL,
    name VARCHAR(255) NOT NULL,
    creation_date DATETIME NOT NULL
);

ALTER TABLE country ADD PRIMARY KEY (code);

