CREATE TABLE user(
    id BIGINT PRIMARY KEY AUTO_INCREMENT, 
    username VARCHAR(255) NOT NULL, 
    password VARCHAR(255),
    enabled BOOLEAN NOT NULL DEFAULT FALSE
); 

CREATE TABLE credential(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    label VARCHAR(255) NOT NULL
);

CREATE TABLE user_credential(
    user_id BIGINT,
    credential_id BIGINT,
    PRIMARY KEY(user_id, credential_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (credential_id) REFERENCES credential(id)
);