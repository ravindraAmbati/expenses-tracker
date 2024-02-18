-- liquibase formatted sql

-- changeset ravindra.ambati:20240202010 labels:v2024.02.02 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS expenses_tracker.h_expenses_details(
    h_id BIGINT NOT NULL PRIMARY KEY,
    record_pair_id BIGINT,
    id BIGINT,
    amount FLOAT,
    paid_by_id BIGINT ,
    paid_to VARCHAR(50),
    date_and_time TIMESTAMP,
    payment_mode_id BIGINT,
    expenses_category_id BIGINT,
    currency_id BIGINT,
    basic_id BIGINT,
    history_type VARCHAR(1),
    action_type VARCHAR(1),
    action_date_and_time TIMESTAMP
);
-- rollback DROP TABLE IF EXISTS expenses_tracker.h_expenses_details;

--changeset ravindra.ambati:20240202011 labels:v2024.02.02 context:postgres
ALTER TABLE expenses_tracker.h_expenses_details ALTER COLUMN h_id SET DEFAULT nextval('expenses_tracker."gen_id_sequence"');
-- rollback ALTER TABLE expenses_tracker.h_expenses_details ALTER COLUMN h_id DROP DEFAULT;

--changeset ravindra.ambati:20240202012 labels:v2024.02.02 context:postgres
ALTER TABLE expenses_tracker.h_expenses_details ADD CONSTRAINT valid_action_type CHECK (action_type IN ('I', 'U', 'D'));
ALTER TABLE expenses_tracker.h_expenses_details ADD CONSTRAINT valid_history_type CHECK (history_type IN ('B', 'A'));
-- rollback ALTER TABLE expenses_tracker.h_expenses_details DROP CONSTRAINT IF EXISTS valid_history_type;
-- rollback ALTER TABLE expenses_tracker.h_expenses_details DROP CONSTRAINT IF EXISTS valid_action_type;