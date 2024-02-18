-- liquibase formatted sql

-- changeset ravindra.ambati:20240218001 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE FUNCTION f_basic_details() RETURNS TRIGGER AS '
    DECLARE
	tmp_record_pair_id BIGINT;
	BEGIN
	tmp_record_pair_id = nextval("expenses_tracker.gen_id_sequence");
        IF (TG_OP = "DELETE") THEN
            INSERT INTO expenses_tracker.h_basic_details(record_pair_id, basic_id, description, is_deleted, last_updated_by, last_updated_date_and_time, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.basic_id, OLD.description, OLD.is_deleted, OLD.last_updated_by, OLD.last_updated_date_and_time, "B", "D",now();
        ELSIF (TG_OP = "UPDATE") THEN
            INSERT INTO expenses_tracker.h_basic_details(record_pair_id, basic_id, description, is_deleted, last_updated_by, last_updated_date_and_time, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.basic_id, OLD.description, OLD.is_deleted, OLD.last_updated_by, OLD.last_updated_date_and_time, "B", "U",now();
			INSERT INTO expenses_tracker.h_basic_details(record_pair_id, basic_id, description, is_deleted, last_updated_by, last_updated_date_and_time, history_type, action_type, action_date_and_time)
			                                    SELECT tmp_record_pair_id, NEW.basic_id, NEW.description, NEW.is_deleted, NEW.last_updated_by, NEW.last_updated_date_and_time, "A", "U",now();
        ELSIF (TG_OP = "INSERT") THEN
			INSERT INTO expenses_tracker.h_basic_details(record_pair_id, basic_id, description, is_deleted, last_updated_by, last_updated_date_and_time, history_type, action_type, action_date_and_time)
			                                    SELECT tmp_record_pair_id, NEW.basic_id, NEW.description, NEW.is_deleted, NEW.last_updated_by, NEW.last_updated_date_and_time, "A", "I",now();
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
' LANGUAGE plpgsql;

-- changeset ravindra.ambati:20240218002 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE TRIGGER t_basic_details
AFTER INSERT OR UPDATE OR DELETE ON expenses_tracker.basic_details
    FOR EACH ROW EXECUTE FUNCTION f_basic_details();