-- liquibase formatted sql

-- changeset ravindra.ambati:20240202007 labels:v2024.02.02 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS expenses_tracker.h_expenses_category_details(
    h_id BIGINT NOT NULL PRIMARY KEY,
    record_pair_id BIGINT,
    id BIGINT NOT NULL,
    expenses_category VARCHAR(100),
    alias JSON,
    basic_id BIGINT,
    history_type VARCHAR(1),
    action_type VARCHAR(1),
    action_date_and_time TIMESTAMP
);
-- rollback DROP TABLE IF EXISTS expenses_tracker.h_expenses_category_details;

--changeset ravindra.ambati:20240202008 labels:v2024.02.02 context:postgres
ALTER TABLE expenses_tracker.h_expenses_category_details ALTER COLUMN h_id SET DEFAULT nextval('expenses_tracker."gen_id_sequence"');
-- rollback ALTER TABLE expenses_tracker.h_expenses_category_details ALTER COLUMN h_id DROP DEFAULT;

--changeset ravindra.ambati:20240202009 labels:v2024.02.02 context:postgres
ALTER TABLE expenses_tracker.h_expenses_category_details ADD CONSTRAINT valid_action_type CHECK (action_type IN ('I', 'U', 'D'));
ALTER TABLE expenses_tracker.h_expenses_category_details ADD CONSTRAINT valid_history_type CHECK (history_type IN ('B', 'A'));
-- rollback ALTER TABLE expenses_tracker.h_expenses_category_details DROP CONSTRAINT IF EXISTS valid_history_type;
-- rollback ALTER TABLE expenses_tracker.h_expenses_category_details DROP CONSTRAINT IF EXISTS valid_action_type;