-- liquibase formatted sql

-- changeset ravindra.ambati:20240127007 labels:v2024.01.27 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE TABLE IF NOT EXISTS expenses_tracker.currency_details(
    id BIGINT NOT NULL PRIMARY KEY,
    currency VARCHAR(3) UNIQUE,
    exchangeRate real,
    defaultCurrency VARCHAR(3),
    basic_id BIGINT
);
-- rollback DROP TABLE IF EXISTS expenses_tracker.currency_details;

--changeset ravindra.ambati:20240127008 labels:v2024.01.27 context:postgres
CREATE INDEX IF NOT EXISTS index_currency_currency_details on expenses_tracker.currency_details(currency);
-- rollback DROP INDEX IF EXISTS expenses_tracker.index_currency_currency_details;

--changeset ravindra.ambati:20240127009 labels:v2024.01.27 context:postgres
ALTER TABLE expenses_tracker.currency_details ALTER COLUMN id SET DEFAULT nextval('expenses_tracker."gen_id_sequence"');
-- rollback ALTER TABLE expenses_tracker.currency_details ALTER COLUMN id DROP DEFAULT;

--changeset ravindra.ambati:20240127010 labels:v2024.01.27 context:postgres
ALTER TABLE expenses_tracker.currency_details ADD CONSTRAINT fk_basic_id_currency_details FOREIGN KEY (basic_id) REFERENCES expenses_tracker.basic_details(basic_id);
-- rollback ALTER TABLE expenses_tracker.currency_details DROP CONSTRAINT IF EXISTS fk_basic_id_currency_details;

--changeset ravindra.ambati:20240127015 labels:v2024.01.27 context:postgres
ALTER TABLE expenses_tracker.currency_details RENAME exchangeRate TO exchange_rate;
ALTER TABLE expenses_tracker.currency_details RENAME defaultCurrency TO default_currency;
-- rollback ALTER TABLE expenses_tracker.currency_details RENAME exchange_rate TO exchangeRate;
-- rollback ALTER TABLE expenses_tracker.currency_details RENAME default_currency TO defaultCurrency;