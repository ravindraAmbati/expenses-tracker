-- liquibase formatted sql

-- changeset ravindra.ambati:20240127007 labels:v2024.01.27 context:h2
-- preconditions onFail:HALT onError:HALT
SET SCHEMA expenses_tracker;
CREATE TABLE IF NOT EXISTS currency_details(
    id BIGINT NOT NULL PRIMARY KEY,
    currency VARCHAR(3) UNIQUE,
    exchangeRate DECFLOAT,
    defaultCurrency VARCHAR(3),
    basic_id BIGINT
);
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP TABLE IF EXISTS currency_details;

--changeset ravindra.ambati:20240127008 labels:v2024.01.27 context:h2
SET SCHEMA expenses_tracker;
CREATE INDEX IF NOT EXISTS index_currency_currency_details on currency_details(currency);
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP INDEX IF EXISTS index_currency_currency_details;

--changeset ravindra.ambati:20240127009 labels:v2024.01.27 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE currency_details ALTER COLUMN IF EXISTS id SET DEFAULT NEXT VALUE FOR gen_id_sequence;
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE currency_details ALTER COLUMN IF EXISTS id DROP DEFAULT;

--changeset ravindra.ambati:20240127010 labels:v2024.01.27 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE currency_details ADD CONSTRAINT IF NOT EXISTS fk_basic_id_currency_details FOREIGN KEY (basic_id) REFERENCES basic_details(basic_id);
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE currency_details DROP CONSTRAINT IF EXISTS fk_basic_id_currency_details;