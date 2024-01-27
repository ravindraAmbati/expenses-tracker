-- liquibase formatted sql

-- changeset ravindra.ambati:20240127011 labels:v2024.01.27 context:h2
-- preconditions onFail:HALT onError:HALT
SET SCHEMA expenses_tracker;
CREATE TABLE IF NOT EXISTS expenses_category_details(
    id BIGINT NOT NULL PRIMARY KEY,
    expenses_category VARCHAR(100) UNIQUE,
    alias JSON,
    basic_id BIGINT
);
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP TABLE IF EXISTS expenses_category_details;

--changeset ravindra.ambati:20240127012 labels:v2024.01.27 context:h2
SET SCHEMA expenses_tracker;
CREATE INDEX IF NOT EXISTS index_expenses_category_expenses_category_details on expenses_category_details(expenses_category);
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP INDEX IF EXISTS index_expenses_category_expenses_category_details;

--changeset ravindra.ambati:20240127013 labels:v2024.01.27 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE expenses_category_details ALTER COLUMN IF EXISTS id SET DEFAULT NEXT VALUE FOR gen_id_sequence;
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE expenses_category_details ALTER COLUMN IF EXISTS id DROP DEFAULT;

--changeset ravindra.ambati:20240127014 labels:v2024.01.27 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE expenses_category_details ADD CONSTRAINT IF NOT EXISTS fk_basic_id_expenses_category_details FOREIGN KEY (basic_id) REFERENCES basic_details(basic_id);
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE expenses_category_details DROP CONSTRAINT IF EXISTS fk_basic_id_expenses_category_details;