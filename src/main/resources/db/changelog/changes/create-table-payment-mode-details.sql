-- liquibase formatted sql

-- changeset ravindra.ambati:20240128001 labels:v2024.01.28 context:h2
-- preconditions onFail:HALT onError:HALT
SET SCHEMA expenses_tracker;
CREATE TABLE IF NOT EXISTS payment_mode_details(
    id BIGINT NOT NULL PRIMARY KEY,
    payment_mode VARCHAR(5),
    card_details VARCHAR(16),
    card_type VARCHAR(1),
    upi_details VARCHAR(50),
    account_details VARCHAR(50),
    basic_id BIGINT
);
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP TABLE IF EXISTS payment_mode_details;

--changeset ravindra.ambati:20240128002 labels:v2024.01.28 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE payment_mode_details ALTER COLUMN IF EXISTS id SET DEFAULT NEXT VALUE FOR gen_id_sequence;
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE payment_mode_details ALTER COLUMN IF EXISTS id DROP DEFAULT;

--changeset ravindra.ambati:20240128003 labels:v2024.01.28 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE payment_mode_details ADD CONSTRAINT IF NOT EXISTS fk_basic_id_payment_mode_details FOREIGN KEY (basic_id) REFERENCES basic_details(basic_id);
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE payment_mode_details DROP CONSTRAINT IF EXISTS fk_basic_id_payment_mode_details;