CREATE TABLE article(
    uuid VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    title VARCHAR(255) NOT NULL
);

ALTER TABLE article ADD PRIMARY KEY (uuid);