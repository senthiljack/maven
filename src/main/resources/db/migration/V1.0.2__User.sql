CREATE TABLE user_table
(
    id                BIGSERIAL,
    created_date      TIMESTAMP,
    last_updated_date TIMESTAMP,
    emp_id            BIGINT,
    first_name        VARCHAR(255),
    last_name         VARCHAR(255),
    full_name         VARCHAR(255),
    email             VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id                BIGSERIAL,
    created_date      TIMESTAMP,
    last_updated_date TIMESTAMP,
    name              VARCHAR(255),
    description       VARCHAR(255),
    is_active         BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
  user_id             BIGINT NOT NULL,
  role_id             BIGINT NOT NULL,
  grant_date          TIMESTAMP,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (role_id)
      REFERENCES roles (id),
  FOREIGN KEY (user_id)
      REFERENCES user_table(id)
);

CREATE TABLE permission
(
    id                BIGSERIAL,
    created_date      TIMESTAMP,
    last_updated_date TIMESTAMP,
    name              VARCHAR(255),
    description       VARCHAR(255),
    is_active         BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE roles_permission (
  role_id             BIGINT NOT NULL,
  permission_id       BIGINT NOT NULL,
  grant_date          TIMESTAMP,
  PRIMARY KEY (role_id, permission_id),
  FOREIGN KEY (role_id)
      REFERENCES roles (id),
  FOREIGN KEY (permission_id)
      REFERENCES permission(id)
);
