-- liquibase formatted sql

-- changeset ravindra.ambati:20240128001 labels:v2024.01.28 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS expenses_tracker.payment_mode_details(
    id BIGINT NOT NULL PRIMARY KEY,
    payment_mode VARCHAR(5),
    card_details VARCHAR(16),
    card_type VARCHAR(1),
    upi_details VARCHAR(50),
    account_details VARCHAR(50),
    basic_id BIGINT
);
-- rollback DROP TABLE IF EXISTS expenses_tracker.payment_mode_details;

--changeset ravindra.ambati:20240128002 labels:v2024.01.28 context:postgres
ALTER TABLE expenses_tracker.payment_mode_details ALTER COLUMN id SET DEFAULT nextval('expenses_tracker."gen_id_sequence"');
-- rollback ALTER TABLE expenses_tracker.payment_mode_details ALTER COLUMN id DROP DEFAULT;

--changeset ravindra.ambati:20240128003 labels:v2024.01.28 context:postgres
ALTER TABLE expenses_tracker.payment_mode_details ADD CONSTRAINT fk_basic_id_payment_mode_details FOREIGN KEY (basic_id) REFERENCES expenses_tracker.basic_details(basic_id);
-- rollback ALTER TABLE expenses_tracker.payment_mode_details DROP CONSTRAINT IF EXISTS fk_basic_id_payment_mode_details;