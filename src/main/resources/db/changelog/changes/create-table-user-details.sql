-- liquibase formatted sql

-- changeset ravindra.ambati:20240127002 labels:v2024.01.27 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS expenses_tracker.user_details(
    id BIGINT NOT NULL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email_id VARCHAR(100) UNIQUE,
    mobile_no BIGINT UNIQUE,
    basic_id BIGINT
);
-- rollback DROP TABLE IF EXISTS expenses_tracker.user_details;

--changeset ravindra.ambati:20240127003 labels:v2024.01.27 context:postgres
CREATE INDEX IF NOT EXISTS index_email_id_user_details on expenses_tracker.user_details(email_id);
CREATE INDEX IF NOT EXISTS index_mobile_no_user_details on expenses_tracker.user_details(mobile_no);
-- rollback DROP INDEX IF EXISTS expenses_tracker.index_email_id_user_details;
-- rollback DROP INDEX IF EXISTS expenses_tracker.index_mobile_no_user_details;

--changeset ravindra.ambati:20240127004 labels:v2024.01.27 context:postgres
ALTER TABLE expenses_tracker.user_details ALTER COLUMN id SET DEFAULT nextval('expenses_tracker."gen_id_sequence"');
-- rollback ALTER TABLE expenses_tracker.user_details ALTER COLUMN id DROP DEFAULT;

--changeset ravindra.ambati:20240127005 labels:v2024.01.27 context:postgres
ALTER TABLE expenses_tracker.user_details ADD CONSTRAINT fk_basic_id_user_details FOREIGN KEY (basic_id) REFERENCES expenses_tracker.basic_details(basic_id);
-- rollback ALTER TABLE expenses_tracker.user_details DROP CONSTRAINT IF EXISTS fk_basic_id_user_details;