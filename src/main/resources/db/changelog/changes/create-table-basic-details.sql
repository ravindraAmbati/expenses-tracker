-- liquibase formatted sql

-- changeset ravindra.ambati:20240124003 labels:v2024.01.24 context:h2
-- preconditions onFail:HALT onError:HALT
SET SCHEMA expenses_tracker;
CREATE TABLE IF NOT EXISTS basic_details(
    basic_id INT NOT NULL PRIMARY KEY,
    description VARCHAR(100),
    is_deleted BOOLEAN,
    last_updated_by INT DEFAULT 99999,
    last_updated_date_and_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP TABLE IF EXISTS basic_details;

--changeset ravindra.ambati:20240124004 labels:v2024.01.24 context:h2
SET SCHEMA expenses_tracker;
CREATE INDEX IF NOT EXISTS index_last_updated_by_basic_details on basic_details(last_updated_by);
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP INDEX IF EXISTS index_last_updated_by_basic_details;

--changeset ravindra.ambati:20240127001 labels:v2024.01.27 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE basic_details ALTER COLUMN IF EXISTS basic_id SET DEFAULT NEXT VALUE FOR gen_id_sequence;
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE basic_details ALTER COLUMN IF EXISTS basic_id DROP DEFAULT;

--changeset ravindra.ambati:20240127006 labels:v2024.01.27 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE basic_details ALTER COLUMN IF EXISTS description VARCHAR(1000);
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE basic_details ALTER COLUMN IF EXISTS description VARCHAR(100);