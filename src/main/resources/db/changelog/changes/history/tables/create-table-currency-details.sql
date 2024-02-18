-- liquibase formatted sql

-- changeset ravindra.ambati:20240202004 labels:v2024.02.02 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS expenses_tracker.h_currency_details(
    h_id BIGINT NOT NULL PRIMARY KEY,
    record_pair_id BIGINT,
    id BIGINT,
    currency VARCHAR(3),
    exchangeRate REAL,
    defaultCurrency VARCHAR(3),
    basic_id BIGINT,
    history_type VARCHAR(1),
    action_type VARCHAR(1),
    action_date_and_time TIMESTAMP
);
-- rollback DROP TABLE IF EXISTS expenses_tracker.h_currency_details;

--changeset ravindra.ambati:20240202005 labels:v2024.02.02 context:postgres
ALTER TABLE expenses_tracker.h_currency_details ALTER COLUMN h_id SET DEFAULT nextval('expenses_tracker."gen_id_sequence"');
-- rollback ALTER TABLE h_currency_details ALTER COLUMN h_id DROP DEFAULT;

--changeset ravindra.ambati:20240202006 labels:v2024.02.02 context:postgres
ALTER TABLE expenses_tracker.h_currency_details ADD CONSTRAINT valid_action_type CHECK (action_type IN ('I', 'U', 'D'));
ALTER TABLE expenses_tracker.h_currency_details ADD CONSTRAINT valid_history_type CHECK (history_type IN ('B', 'A'));
-- rollback ALTER TABLE expenses_tracker.h_currency_details DROP CONSTRAINT IF EXISTS valid_history_type;
-- rollback ALTER TABLE expenses_tracker.h_currency_details DROP CONSTRAINT IF EXISTS valid_action_type;