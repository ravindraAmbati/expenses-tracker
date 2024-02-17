-- liquibase formatted sql

-- changeset ravindra.ambati:20240202001 labels:v2024.02.02 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS expenses_tracker.h_basic_details(
    h_id BIGINT NOT NULL PRIMARY KEY,
    record_pair_id BIGINT,
    basic_id BIGINT,
    description VARCHAR(100),
    is_deleted BOOLEAN,
    last_updated_by BIGINT,
    last_updated_date_and_time TIMESTAMP,
    history_type VARCHAR(1),
    action_type VARCHAR(1),
    action_by VARCHAR(100),
    action_from VARCHAR(100),
    action_date_and_time TIMESTAMP
);
-- rollback DROP TABLE IF EXISTS expenses_tracker.h_basic_details;

--changeset ravindra.ambati:20240202002 labels:v2024.02.02 context:postgres
ALTER TABLE expenses_tracker.h_basic_details ALTER COLUMN h_id SET DEFAULT nextval('expenses_tracker."gen_id_sequence"');
-- rollback ALTER TABLE expenses_tracker.h_basic_details ALTER COLUMN h_id DROP DEFAULT;

--changeset ravindra.ambati:20240202003 labels:v2024.02.02 context:postgres
ALTER TABLE expenses_tracker.h_basic_details ADD CONSTRAINT valid_action_type CHECK (action_type IN ('A', 'U', 'D'));
ALTER TABLE expenses_tracker.h_basic_details ADD CONSTRAINT valid_history_type CHECK (history_type IN ('B', 'A'));
-- rollback ALTER TABLE expenses_tracker.h_basic_details DROP CONSTRAINT IF EXISTS valid_history_type;
-- rollback ALTER TABLE expenses_tracker.h_basic_details DROP CONSTRAINT IF EXISTS valid_action_type;