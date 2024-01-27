-- liquibase formatted sql

-- changeset ravindra.ambati:20240124002 labels:v2024.01.24 context:h2
SET SCHEMA expenses_tracker;
CREATE SEQUENCE IF NOT EXISTS gen_id_sequence MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 1 NOCACHE NOCYCLE;
-- rollback SET SCHEMA expenses_tracker;
-- rollback DROP SEQUENCE IF EXISTS gen_id_sequence;