CREATE TABLE user(id, uuid, name) (
    id BIGINT NOT NULL AUTO_INCREMENT,
    uuid VARCHAR(38) NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uk_user_uuid_user UNIQUE(uuid),
    CONSTRAINT uk_user_name UNIQUE(name),
);