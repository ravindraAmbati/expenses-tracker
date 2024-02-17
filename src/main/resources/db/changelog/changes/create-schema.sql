-- liquibase formatted sql

-- changeset ravindra.ambati:20240124001 labels:v2024.01.24 context:postgres
CREATE SCHEMA IF NOT EXISTS expenses_tracker;
-- rollback DROP SCHEMA IF EXISTS expenses_tracker;