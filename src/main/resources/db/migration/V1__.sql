CREATE TABLE address
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NULL,
    last_updated_at datetime              NULL,
    is_deleted      BIT(1)                NOT NULL,
    city            VARCHAR(255)          NULL,
    street          VARCHAR(255)          NULL,
    zip             VARCHAR(255)          NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE name
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NULL,
    last_updated_at datetime              NULL,
    is_deleted      BIT(1)                NOT NULL,
    firstname       VARCHAR(255)          NULL,
    lastname        VARCHAR(255)          NULL,
    CONSTRAINT pk_name PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NULL,
    last_updated_at datetime              NULL,
    is_deleted      BIT(1)                NOT NULL,
    name            VARCHAR(255)          NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE token
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NULL,
    last_updated_at datetime              NULL,
    is_deleted      BIT(1)                NOT NULL,
    value           VARCHAR(255)          NULL,
    user_id         BIGINT                NULL,
    expiry_at       datetime              NULL,
    CONSTRAINT pk_token PRIMARY KEY (id)
);

CREATE TABLE user
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    created_at        datetime              NULL,
    last_updated_at   datetime              NULL,
    is_deleted        BIT(1)                NOT NULL,
    email             VARCHAR(255)          NULL,
    username          VARCHAR(255)          NULL,
    password          VARCHAR(255)          NULL,
    name_id           BIGINT                NULL,
    phone             VARCHAR(255)          NULL,
    is_email_verified BIT(1)                NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_address
(
    user_id    BIGINT NOT NULL,
    address_id BIGINT NOT NULL
);

CREATE TABLE user_roles
(
    user_id  BIGINT NOT NULL,
    roles_id BIGINT NOT NULL
);

ALTER TABLE user_address
    ADD CONSTRAINT uc_user_address_address UNIQUE (address_id);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_NAME FOREIGN KEY (name_id) REFERENCES name (id);

ALTER TABLE user_address
    ADD CONSTRAINT fk_useadd_on_address FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE user_address
    ADD CONSTRAINT fk_useadd_on_user FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_roles FOREIGN KEY (roles_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id);