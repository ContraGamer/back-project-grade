CREATE DATABASE csrf_test;

CREATE TABLE parameter (
    id BIGSERIAL NOT NULL,
    key VARCHAR(100) NOT NULL,
    value VARCHAR(500) NOT NULL,
    state VARCHAR(1) NOT NULL,
    of VARCHAR(50),
    additional VARCHAR(500),
    PRIMARY KEY (id)
);

CREATE TABLE permit (
    id BIGSERIAL NOT NULL,
    state VARCHAR(1) DEFAULT 'A' NOT NULL,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    icon VARCHAR(50) NOT NULL,
    parent BIGINT,
    url VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE role (
    id BIGSERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    state VARCHAR(1) DEFAULT 'A' NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE permit_role(
    id BIGSERIAL NOT NULL,
    permit BIGINT NOT NULL,
    role BIGINT NOT NULL,
    CONSTRAINT fk_permit FOREIGN KEY (permit) REFERENCES permit(id),
    CONSTRAINT fk_role FOREIGN KEY (role) REFERENCES role(id),
    PRIMARY KEY (id)
);

CREATE TABLE final_user(
    id BIGSERIAL NOT NULL,
    name VARCHAR(300) NOT NULL,
    auth_type VARCHAR(1) DEFAULT 'A' NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(20),
    cellphone VARCHAR(12),
    role BIGINT NOT NULL,
    creation_date TIMESTAMP DEFAULT now() NOT NULL,
    address VARCHAR(100),
    identification VARCHAR(15) UNIQUE,
    id_type VARCHAR(2),
    state VARCHAR(1) DEFAULT 'A' NOT NULL,
    token VARCHAR,
    PRIMARY KEY (id),
    CONSTRAINT fk_role FOREIGN KEY (role) REFERENCES role(id)
);

CREATE TABLE type_acount(
    id BIGSERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    state VARCHAR(1) DEFAULT 'A' NOT NULL,
    icon VARCHAR(500),
    PRIMARY KEY (id)
);

CREATE TABLE acount(
    id BIGSERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    amount VARCHAR(100) NOT NULL,
    number VARCHAR(20) NOT NULL,
    type_acount BIGINT NOT NULL,
    state VARCHAR(1) DEFAULT 'A' NOT NULL,
    CONSTRAINT fk_type_acount FOREIGN KEY (type_acount) REFERENCES type_acount(id),
    PRIMARY KEY (id)
);

CREATE TABLE users_acount(
    id BIGSERIAL NOT NULL,
    final_user BIGINT NOT NULL,
    acount BIGINT NOT NULL,
    state VARCHAR(1) DEFAULT 'A' NOT NULL,
    CONSTRAINT fk_final_user FOREIGN KEY (final_user) REFERENCES final_user(id),
    CONSTRAINT fk_acount FOREIGN KEY (acount) REFERENCES acount(id),
    PRIMARY KEY (id)
);

CREATE TABLE movements(
    id BIGSERIAL NOT NULL,
    final_user BIGINT NOT NULL,
    acount_first VARCHAR NOT NULL,
    acount_second VARCHAR NOT NULL,
    amount VARCHAR NOT NULL,
    type_move VARCHAR NOT NULL,
    code VARCHAR NOT NULL,
    CONSTRAINT fk_final_user FOREIGN KEY (final_user) REFERENCES final_user(id),
    PRIMARY KEY (id)
);


-- ALTER TABLE parameter ADD COLUMN additional VARCHAR(500);
-- ALTER TABLE parameter ADD COLUMN of VARCHAR(50);