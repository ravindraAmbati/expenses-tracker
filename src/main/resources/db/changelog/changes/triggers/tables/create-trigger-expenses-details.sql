-- liquibase formatted sql

-- changeset ravindra.ambati:20240218007 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE FUNCTION f_expenses_details() RETURNS TRIGGER AS '
    DECLARE
	tmp_record_pair_id BIGINT;
	BEGIN
	tmp_record_pair_id = nextval("expenses_tracker.gen_id_sequence");
        IF (TG_OP = "DELETE") THEN
            INSERT INTO expenses_tracker.h_expenses_details(record_pair_id, id, amount, paid_by_id, paid_to, date_and_time, payment_mode_id, expenses_category_id, currency_id, basic_id, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.id, OLD.amount, OLD.paid_by_id, OLD.paid_to, OLD.date_and_time, OLD.payment_mode_id, OLD.expenses_category_id, OLD.currency_id, OLD.basic_id, "B", "D", now();
        ELSIF (TG_OP = "UPDATE") THEN
            INSERT INTO expenses_tracker.h_expenses_details(record_pair_id, id, amount, paid_by_id, paid_to, date_and_time, payment_mode_id, expenses_category_id, currency_id, basic_id, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.id, OLD.amount, OLD.paid_by_id, OLD.paid_to, OLD.date_and_time, OLD.payment_mode_id, OLD.expenses_category_id, OLD.currency_id, OLD.basic_id, "B", "U", now();
			INSERT INTO expenses_tracker.h_expenses_details(record_pair_id, id, amount, paid_by_id, paid_to, date_and_time, payment_mode_id, expenses_category_id, currency_id, basic_id, history_type, action_type, action_date_and_time)
			                                    SELECT tmp_record_pair_id, NEW.id, NEW.amount, NEW.paid_by_id, NEW.paid_to, NEW.date_and_time, NEW.payment_mode_id, NEW.expenses_category_id, NEW.currency_id, NEW.basic_id, "A", "U", now();
        ELSIF (TG_OP = "INSERT") THEN
			INSERT INTO expenses_tracker.h_expenses_details(record_pair_id, id, amount, paid_by_id, paid_to, date_and_time, payment_mode_id, expenses_category_id, currency_id, basic_id, history_type, action_type, action_date_and_time) 
			                                    SELECT tmp_record_pair_id, NEW.id, NEW.amount, NEW.paid_by_id, NEW.paid_to, NEW.date_and_time, NEW.payment_mode_id, NEW.expenses_category_id, NEW.currency_id, NEW.basic_id, "A", "I", now();
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
' LANGUAGE plpgsql;

-- changeset ravindra.ambati:20240218008 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE TRIGGER t_expenses_details
AFTER INSERT OR UPDATE OR DELETE ON expenses_tracker.expenses_details
    FOR EACH ROW EXECUTE FUNCTION f_expenses_details();