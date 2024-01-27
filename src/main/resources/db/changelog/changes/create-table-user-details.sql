-- liquibase formatted sql

-- changeset ravindra.ambati:20240127002 labels:v2024.01.27 context:h2
-- preconditions onFail:HALT onError:HALT
SET SCHEMA expenses_tracker;
CREATE TABLE IF NOT EXISTS user_details(
    id BIGINT NOT NULL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email_id VARCHAR(100) UNIQUE,
    mobile_no BIGINT UNIQUE,
    basic_id BIGINT
);
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP TABLE IF EXISTS user_details;

--changeset ravindra.ambati:20240127003 labels:v2024.01.27 context:h2
SET SCHEMA expenses_tracker;
CREATE INDEX IF NOT EXISTS index_email_id_user_details on user_details(email_id);
CREATE INDEX IF NOT EXISTS index_mobile_no_user_details on user_details(mobile_no);
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP INDEX IF EXISTS index_email_id_user_details;
-- rollback DROP INDEX IF EXISTS index_email_id_user_details;

--changeset ravindra.ambati:20240127004 labels:v2024.01.27 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE user_details ALTER COLUMN IF EXISTS id SET DEFAULT NEXT VALUE FOR gen_id_sequence;
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE user_details ALTER COLUMN IF EXISTS id DROP DEFAULT;

--changeset ravindra.ambati:20240127005 labels:v2024.01.27 context:h2
SET SCHEMA expenses_tracker;
ALTER TABLE user_details ADD CONSTRAINT IF NOT EXISTS fk_basic_id_user_details FOREIGN KEY (basic_id) REFERENCES basic_details(basic_id);
-- rollback SET SCHEMA expenses_tracker;
-- rollback ALTER TABLE user_details DROP CONSTRAINT IF EXISTS fk_basic_id_user_details;