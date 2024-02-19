-- liquibase formatted sql

-- changeset ravindra.ambati:20240218013 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE FUNCTION expenses_tracker.f_user_details() RETURNS TRIGGER AS '
    DECLARE
	tmp_record_pair_id BIGINT;
	BEGIN
	tmp_record_pair_id = nextval("expenses_tracker.gen_id_sequence");
        IF (TG_OP = "DELETE") THEN
            INSERT INTO expenses_tracker.h_user_details(record_pair_id, id, first_name, last_name, email_id, mobile_no, basic_id, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.id, OLD.first_name, OLD.last_name, OLD.email_id, OLD.mobile_no, OLD.basic_id, "B", "D", now();
        ELSIF (TG_OP = "UPDATE") THEN
            INSERT INTO expenses_tracker.h_user_details(record_pair_id, id, first_name, last_name, email_id, mobile_no, basic_id, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.id, OLD.first_name, OLD.last_name, OLD.email_id, OLD.mobile_no, OLD.basic_id, "B", "U", now();
			INSERT INTO expenses_tracker.h_user_details(record_pair_id, id, first_name, last_name, email_id, mobile_no, basic_id, history_type, action_type, action_date_and_time)
			                                    SELECT tmp_record_pair_id, NEW.id, NEW.first_name, NEW.last_name, NEW.email_id, NEW.mobile_no, NEW.basic_id, "A", "U", now();
        ELSIF (TG_OP = "INSERT") THEN
			INSERT INTO expenses_tracker.h_user_details(record_pair_id, id, first_name, last_name, email_id, mobile_no, basic_id, history_type, action_type, action_date_and_time) 
			                                    SELECT tmp_record_pair_id, NEW.id, NEW.first_name, NEW.last_name, NEW.email_id, NEW.mobile_no, NEW.basic_id, "A", "I", now();
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
' LANGUAGE plpgsql;

-- changeset ravindra.ambati:20240218014 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE TRIGGER t_user_details
AFTER INSERT OR UPDATE OR DELETE ON expenses_tracker.user_details
    FOR EACH ROW EXECUTE FUNCTION expenses_tracker.f_user_details();