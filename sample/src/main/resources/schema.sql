CREATE TABLE user(
    id BIGINT PRIMARY KEY AUTO_INCREMENT, 
    username VARCHAR(255) NOT NULL, 
    password VARCHAR(255),
    enabled BOOLEAN NOT NULL DEFAULT FALSE
); 

CREATE TABLE permission(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    label VARCHAR(255) NOT NULL
);

CREATE TABLE user_permission(
    id BIGINT,
    user_id BIGINT,
    permission_id BIGINT,
    PRIMARY KEY(id),
    UNIQUE(user_id, permission_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
);