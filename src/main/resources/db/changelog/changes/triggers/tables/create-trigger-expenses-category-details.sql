-- liquibase formatted sql

-- changeset ravindra.ambati:20240218005 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE FUNCTION f_expenses_category_details() RETURNS TRIGGER AS '
    DECLARE
	tmp_record_pair_id BIGINT;
	BEGIN
	tmp_record_pair_id = nextval("expenses_tracker.gen_id_sequence");
        IF (TG_OP = "DELETE") THEN
            INSERT INTO expenses_tracker.h_expenses_category_details(record_pair_id, id, expenses_category, alias, basic_id, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.id, OLD.expenses_category, OLD.alias, OLD.basic_id, "B", "D", now();
        ELSIF (TG_OP = "UPDATE") THEN
            INSERT INTO expenses_tracker.h_expenses_category_details(record_pair_id, id, expenses_category, alias, basic_id, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.id, OLD.expenses_category, OLD.alias, OLD.basic_id, "B", "U", now();
			INSERT INTO expenses_tracker.h_expenses_category_details(record_pair_id, id, expenses_category, alias, basic_id, history_type, action_type, action_date_and_time)
			                                    SELECT tmp_record_pair_id, NEW.id, NEW.expenses_category, NEW.alias, NEW.basic_id, "A", "U", now();
        ELSIF (TG_OP = "INSERT") THEN
			INSERT INTO expenses_tracker.h_expenses_category_details(record_pair_id, id, expenses_category, alias, basic_id, history_type, action_type, action_date_and_time) 
			                                    SELECT tmp_record_pair_id, NEW.id, NEW.expenses_category, NEW.alias, NEW.basic_id, "A", "I", now();
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
' LANGUAGE plpgsql;

-- changeset ravindra.ambati:20240218006 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE TRIGGER t_expenses_category_details
AFTER INSERT OR UPDATE OR DELETE ON expenses_tracker.expenses_category_details
    FOR EACH ROW EXECUTE FUNCTION f_expenses_category_details();