-- liquibase formatted sql

-- changeset ravindra.ambati:20240202001 labels:v2024.02.02 context:h2
-- preconditions onFail:HALT onError:HALT
SET SCHEMA expenses_tracker;
CREATE TABLE IF NOT EXISTS h_basic_details(
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
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP TABLE IF EXISTS h_basic_details;

--changeset ravindra.ambati:20240202002 labels:v2024.02.02 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE h_basic_details ALTER COLUMN IF EXISTS h_id SET DEFAULT NEXT VALUE FOR gen_id_sequence;
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE h_basic_details ALTER COLUMN IF EXISTS h_id DROP DEFAULT;

--changeset ravindra.ambati:20240202003 labels:v2024.02.02 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE h_basic_details ADD CONSTRAINT IF NOT EXISTS valid_action_type CHECK (action_type IN ('A', 'U', 'D'));
ALTER TABLE h_basic_details ADD CONSTRAINT IF NOT EXISTS valid_history_type CHECK (history_type IN ('B', 'A'));
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE h_basic_details DROP CONSTRAINT IF EXISTS valid_history_type;
-- rollback ALTER TABLE h_basic_details DROP CONSTRAINT IF EXISTS valid_action_type;