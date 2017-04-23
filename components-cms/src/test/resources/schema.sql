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

CREATE TABLE article(
    id VARCHAR(36) NOT NULL,
    creation_date DATETIME NOT NULL,
    author_id VARCHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    url_friendly_title VARCHAR(255) NOT NULL,
    description VARCHAR(500) NOT NULL,
    content VARCHAR(5000),
    publication_date DATETIME,
    published BOOLEAN NOT NULL DEFAULT FALSE
);

ALTER TABLE article ADD PRIMARY KEY (id);
ALTER TABLE article ADD CONSTRAINT fk_article_account_id FOREIGN KEY (author_id) REFERENCES account (id);

CREATE TABLE news(
    id VARCHAR(26) NOT NULL,
    creation_date DATETIME NOT NULL,
    article_id VARCHAR(36) NOT NULL,
    author_id VARCHAR(36) NOT NULL,
    content VARCHAR(500) NOT NULL,
    source VARCHAR(500)
);

ALTER TABLE news ADD PRIMARY KEY (id);
ALTER TABLE news ADD CONSTRAINT fk_news_article_id FOREIGN KEY (article_id) REFERENCES article (id);
ALTER TABLE news ADD CONSTRAINT fk_news_account_id FOREIGN KEY (author_id) REFERENCES account (id);


