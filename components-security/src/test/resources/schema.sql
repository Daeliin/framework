CREATE TABLE account (
    id VARCHAR(36) NOT NULL,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    signup_date DATETIME NOT NULL
);

ALTER TABLE account ADD PRIMARY KEY (id);
ALTER TABLE account ADD CONSTRAINT uk_account_username UNIQUE (username);
ALTER TABLE account ADD CONSTRAINT uk_account_email UNIQUE (email);

CREATE TABLE permission (
    label VARCHAR(255) NOT NULL
);

ALTER TABLE permission ADD PRIMARY KEY (label);

CREATE TABLE account_permission (
    account_id VARCHAR(36) NOT NULL,
    permission_label VARCHAR(255) NOT NULL
);

ALTER TABLE account_permission ADD PRIMARY KEY (account_id, permission_label);
ALTER TABLE account_permission ADD CONSTRAINT fk_account_permission_account_id FOREIGN KEY (account_id) REFERENCES account (id);
ALTER TABLE account_permission ADD CONSTRAINT fk_account_permission_permission_label FOREIGN KEY (permission_label) REFERENCES permission (label);
