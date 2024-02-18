-- liquibase formatted sql

-- changeset ravindra.ambati:20240218011 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE FUNCTION f_payment_mode_details() RETURNS TRIGGER AS '
    DECLARE
	tmp_record_pair_id BIGINT;
	BEGIN
	tmp_record_pair_id = nextval("expenses_tracker.gen_id_sequence");
        IF (TG_OP = "DELETE") THEN
            INSERT INTO expenses_tracker.h_payment_mode_details(record_pair_id, id, payment_mode, card_details, card_type, upi_details, account_details, basic_id, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.id, OLD.payment_mode, OLD.card_details, OLD.card_type, OLD.upi_details, OLD.account_details, OLD.basic_id, "B", "D", now();
        ELSIF (TG_OP = "UPDATE") THEN
            INSERT INTO expenses_tracker.h_payment_mode_details(record_pair_id, id, payment_mode, card_details, card_type, upi_details, account_details, basic_id, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.id, OLD.payment_mode, OLD.card_details, OLD.card_type, OLD.upi_details, OLD.account_details, OLD.basic_id, "B", "U", now();
			INSERT INTO expenses_tracker.h_payment_mode_details(record_pair_id, id, payment_mode, card_details, card_type, upi_details, account_details, basic_id, history_type, action_type, action_date_and_time)
			                                    SELECT tmp_record_pair_id, NEW.id, NEW.payment_mode, NEW.card_details, NEW.card_type, NEW.upi_details, NEW.account_details, NEW.basic_id, "A", "U", now();
        ELSIF (TG_OP = "INSERT") THEN
			INSERT INTO expenses_tracker.h_payment_mode_details(record_pair_id, id, payment_mode, card_details, card_type, upi_details, account_details, basic_id, history_type, action_type, action_date_and_time) 
			                                    SELECT tmp_record_pair_id, NEW.id, NEW.payment_mode, NEW.card_details, NEW.card_type, NEW.upi_details, NEW.account_details, NEW.basic_id, "A", "I", now();
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
' LANGUAGE plpgsql;

-- changeset ravindra.ambati:20240218012 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE TRIGGER t_payment_mode_details
AFTER INSERT OR UPDATE OR DELETE ON expenses_tracker.payment_mode_details
    FOR EACH ROW EXECUTE FUNCTION f_payment_mode_details();