-- liquibase formatted sql

-- changeset ravindra.ambati:20240127011 labels:v2024.01.27 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS expenses_tracker.expenses_category_details(
    id BIGINT NOT NULL PRIMARY KEY,
    expenses_category VARCHAR(100) UNIQUE,
    alias JSON,
    basic_id BIGINT
);
-- rollback DROP TABLE IF EXISTS expenses_tracker.expenses_category_details;

--changeset ravindra.ambati:20240127012 labels:v2024.01.27 context:postgres
CREATE INDEX IF NOT EXISTS index_expenses_category_expenses_category_details on expenses_tracker.expenses_category_details(expenses_category);
-- rollback DROP INDEX IF EXISTS expenses_tracker.index_expenses_category_expenses_category_details;

--changeset ravindra.ambati:20240127013 labels:v2024.01.27 context:postgres
ALTER TABLE expenses_tracker.expenses_category_details ALTER COLUMN id SET DEFAULT nextval('expenses_tracker."gen_id_sequence"');
-- rollback ALTER TABLE expenses_tracker.expenses_category_details ALTER COLUMN id DROP DEFAULT;

--changeset ravindra.ambati:20240127014 labels:v2024.01.27 context:postgres
ALTER TABLE expenses_tracker.expenses_category_details ADD CONSTRAINT fk_basic_id_expenses_category_details FOREIGN KEY (basic_id) REFERENCES expenses_tracker.basic_details(basic_id);
-- rollback ALTER TABLE expenses_tracker.expenses_category_details DROP CONSTRAINT IF EXISTS fk_basic_id_expenses_category_details;