-- liquibase formatted sql

-- changeset ravindra.ambati:20240124003 labels:v2024.01.24 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS expenses_tracker.basic_details(
    basic_id BIGINT NOT NULL PRIMARY KEY,
    description VARCHAR(100),
    is_deleted BOOLEAN,
    last_updated_by BIGINT DEFAULT 99999,
    last_updated_date_and_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- rollback DROP TABLE IF EXISTS expenses_tracker.basic_details;

--changeset ravindra.ambati:20240124004 labels:v2024.01.24 context:postgres
CREATE INDEX IF NOT EXISTS index_last_updated_by_basic_details on expenses_tracker.basic_details(last_updated_by);
-- rollback DROP INDEX IF EXISTS expenses_tracker.index_last_updated_by_basic_details;

--changeset ravindra.ambati:20240127001 labels:v2024.01.27 context:postgres
ALTER TABLE expenses_tracker.basic_details ALTER COLUMN basic_id SET DEFAULT nextval('expenses_tracker."gen_id_sequence"');
-- rollback ALTER TABLE expenses_tracker.basic_details ALTER COLUMN basic_id DROP DEFAULT;

--changeset ravindra.ambati:20240127006 labels:v2024.01.27 context:postgres
ALTER TABLE expenses_tracker.basic_details ALTER COLUMN description TYPE VARCHAR(1000);
-- rollback ALTER TABLE expenses_tracker.basic_details ALTER COLUMN description TYPE VARCHAR(100);