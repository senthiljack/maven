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

CREATE TABLE outward
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
    PRIMARY KEY (id)
);


CREATE TABLE outward_items
(
    id           BIGSERIAL,
    outward_id   BIGINT,
    accessory_id BIGINT,
    name        VARCHAR(255),
    model        VARCHAR(255),
    serial_no    VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_outward
        FOREIGN KEY (outward_id)
            REFERENCES outward (id),
    CONSTRAINT fk_accessory
        FOREIGN KEY (accessory_id)
            REFERENCES accessory_type (id)
);

CREATE TABLE onboard
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
    PRIMARY KEY (id)
);


CREATE TABLE onboard_items
(
    id           BIGSERIAL,
    onboard_id   BIGINT,
    accessory_id BIGINT,
    name        VARCHAR(255),
    model        VARCHAR(255),
    serial_no    VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_onboard
        FOREIGN KEY (onboard_id)
            REFERENCES onboard (id),
    CONSTRAINT fk_accessory
        FOREIGN KEY (accessory_id)
            REFERENCES accessory_type (id)
);

CREATE TABLE reliving
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
    PRIMARY KEY (id)
);


CREATE TABLE reliving_items
(
    id           BIGSERIAL,
    reliving_id  BIGINT,
    accessory_id BIGINT,
    name        VARCHAR(255),
    model        VARCHAR(255),
    serial_no    VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_reliving
        FOREIGN KEY (reliving_id)
            REFERENCES reliving (id),
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



