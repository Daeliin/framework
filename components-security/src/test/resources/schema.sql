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
