-- liquibase formatted sql

-- changeset ravindra.ambati:20240128004 labels:v2024.01.28 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS expenses_tracker.expenses_details(
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
-- rollback DROP TABLE IF EXISTS expenses_tracker.expenses_details;

--changeset ravindra.ambati:20240128005 labels:v2024.01.28 context:postgres
ALTER TABLE expenses_tracker.expenses_details ALTER COLUMN id SET DEFAULT nextval('expenses_tracker."gen_id_sequence"');
-- rollback ALTER TABLE expenses_tracker.expenses_details ALTER COLUMN id DROP DEFAULT;

--changeset ravindra.ambati:20240128006 labels:v2024.01.28 context:postgres
ALTER TABLE expenses_tracker.expenses_details ADD CONSTRAINT fk_paid_by_id_expenses_details FOREIGN KEY (paid_by_id) REFERENCES expenses_tracker.user_details(id);
ALTER TABLE expenses_tracker.expenses_details ADD CONSTRAINT fk_payment_mode_id_expenses_details FOREIGN KEY (payment_mode_id) REFERENCES expenses_tracker.payment_mode_details(id);
ALTER TABLE expenses_tracker.expenses_details ADD CONSTRAINT fk_expenses_category_id_expenses_details FOREIGN KEY (expenses_category_id) REFERENCES expenses_tracker.expenses_category_details(id);
ALTER TABLE expenses_tracker.expenses_details ADD CONSTRAINT fk_currency_id_expenses_details FOREIGN KEY (currency_id) REFERENCES expenses_tracker.currency_details(id);
ALTER TABLE expenses_tracker.expenses_details ADD CONSTRAINT fk_basic_id_expenses_details FOREIGN KEY (basic_id) REFERENCES expenses_tracker.basic_details(basic_id);
-- rollback ALTER TABLE expenses_tracker.expenses_details DROP CONSTRAINT IF EXISTS fk_paid_by_id_expenses_details;
-- rollback ALTER TABLE expenses_tracker.expenses_details DROP CONSTRAINT IF EXISTS fk_payment_mode_id_expenses_details;
-- rollback ALTER TABLE expenses_tracker.expenses_details DROP CONSTRAINT IF EXISTS fk_expenses_category_id_expenses_details;
-- rollback ALTER TABLE expenses_tracker.expenses_details DROP CONSTRAINT IF EXISTS fk_currency_id_expenses_details;
-- rollback ALTER TABLE expenses_tracker.expenses_details DROP CONSTRAINT IF EXISTS fk_basic_id_expenses_details;