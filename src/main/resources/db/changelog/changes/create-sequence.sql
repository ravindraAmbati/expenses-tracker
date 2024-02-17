-- liquibase formatted sql

-- changeset ravindra.ambati:20240124002 labels:v2024.01.24 context:postgres
CREATE SEQUENCE IF NOT EXISTS expenses_tracker.gen_id_sequence MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 1;
-- rollback DROP SEQUENCE IF EXISTS expenses_tracker.gen_id_sequence;