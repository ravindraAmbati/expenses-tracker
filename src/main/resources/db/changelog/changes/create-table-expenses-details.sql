-- liquibase formatted sql

-- changeset ravindra.ambati:20240128004 labels:v2024.01.28 context:h2
-- preconditions onFail:HALT onError:HALT
SET SCHEMA expenses_tracker;
CREATE TABLE IF NOT EXISTS expenses_details(
    id BIGINT NOT NULL PRIMARY KEY,
    amount FLOAT,
    paid_by_id BIGINT NOT NULL,
    paid_to VARCHAR(50),
    date_and_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_mode_id BIGINT NOT NULL,
    expenses_category_id BIGINT NOT NULL,
    currency_id BIGINT NOT NULL,
    basic_id BIGINT
);
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP TABLE IF EXISTS expenses_details;

--changeset ravindra.ambati:20240128005 labels:v2024.01.28 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE expenses_details ALTER COLUMN IF EXISTS id SET DEFAULT NEXT VALUE FOR gen_id_sequence;
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE expenses_details ALTER COLUMN IF EXISTS id DROP DEFAULT;

--changeset ravindra.ambati:20240128006 labels:v2024.01.28 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE expenses_details ADD CONSTRAINT IF NOT EXISTS fk_paid_by_id_expenses_details FOREIGN KEY (paid_by_id) REFERENCES user_details(id);
ALTER TABLE expenses_details ADD CONSTRAINT IF NOT EXISTS fk_payment_mode_id_expenses_details FOREIGN KEY (payment_mode_id) REFERENCES payment_mode_details(id);
ALTER TABLE expenses_details ADD CONSTRAINT IF NOT EXISTS fk_expenses_category_id_expenses_details FOREIGN KEY (expenses_category_id) REFERENCES expenses_category_details(id);
ALTER TABLE expenses_details ADD CONSTRAINT IF NOT EXISTS fk_currency_id_expenses_details FOREIGN KEY (currency_id) REFERENCES currency_details(id);
ALTER TABLE expenses_details ADD CONSTRAINT IF NOT EXISTS fk_basic_id_expenses_details FOREIGN KEY (basic_id) REFERENCES basic_details(basic_id);
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE expenses_details DROP CONSTRAINT IF EXISTS fk_paid_by_id_expenses_details;
-- rollback ALTER TABLE expenses_details DROP CONSTRAINT IF EXISTS fk_payment_mode_id_expenses_details;
-- rollback ALTER TABLE expenses_details DROP CONSTRAINT IF EXISTS fk_expenses_category_id_expenses_details;
-- rollback ALTER TABLE expenses_details DROP CONSTRAINT IF EXISTS fk_currency_id_expenses_details;
-- rollback ALTER TABLE expenses_details DROP CONSTRAINT IF EXISTS fk_basic_id_expenses_details;