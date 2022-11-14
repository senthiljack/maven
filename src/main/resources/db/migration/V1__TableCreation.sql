CREATE TABLE category
(
    id                BIGSERIAL,
    created_date      TIMESTAMP,
    last_updated_date TIMESTAMP,
    created_by        BIGINT,
    last_updated_by   BIGINT,
    name              VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE accessory_type
(
    id                BIGSERIAL,
    created_date      TIMESTAMP,
    last_updated_date TIMESTAMP,
    created_by        BIGINT,
    last_updated_by   BIGINT,
    name              VARCHAR(255),
    is_active         BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE inward
(
    id                 BIGSERIAL,
    created_date       TIMESTAMP,
    last_updated_date  TIMESTAMP,
    created_by         BIGINT,
    last_updated_by    BIGINT,
    others             BOOLEAN,
    name               VARCHAR(255),
    vendor_name        VARCHAR(255),
    provider_id        BIGINT,
    provider_name      VARCHAR(255),
    receiver_id        BIGINT,
    receiver_name      VARCHAR(255),
    provider_signature VARCHAR(1000),
    receiver_signature VARCHAR(1000),
    version            BIGINT,
    PRIMARY KEY (id)
);


CREATE TABLE inward_items
(
    id           BIGSERIAL,
    inward_id    BIGINT,
    accessory_id BIGINT,
    name        VARCHAR(255),
    model        VARCHAR(255),
    serial_no    VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_inward
        FOREIGN KEY (inward_id)
            REFERENCES inward (id),
    CONSTRAINT fk_accessory
        FOREIGN KEY (accessory_id)
            REFERENCES accessory_type (id)
);


CREATE TABLE emp_details
(
    id                BIGSERIAL,
    created_date      TIMESTAMP,
    last_updated_date TIMESTAMP,
    created_by        BIGINT,
    last_updated_by   BIGINT,
    emp_id            BIGINT,
    first_name        VARCHAR(255),
    last_name         VARCHAR(255),
    full_name         VARCHAR(255),
    email             VARCHAR(255),
    PRIMARY KEY (id)
);



