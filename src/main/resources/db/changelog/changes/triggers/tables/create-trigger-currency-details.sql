-- liquibase formatted sql

-- changeset ravindra.ambati:20240218003 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE FUNCTION f_currency_details() RETURNS TRIGGER AS '
    DECLARE
	tmp_record_pair_id BIGINT;
	BEGIN
	tmp_record_pair_id = nextval("expenses_tracker.gen_id_sequence");
        IF (TG_OP = "DELETE") THEN
            INSERT INTO expenses_tracker.h_currency_details(record_pair_id, id, currency, exchangeRate, defaultCurrency, basic_id, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.id, OLD.currency, OLD.exchangeRate, OLD.defaultCurrency, OLD.basic_id, "B", "D",now();
        ELSIF (TG_OP = "UPDATE") THEN
            INSERT INTO expenses_tracker.h_currency_details(record_pair_id, id, currency, exchangeRate, defaultCurrency, basic_id, history_type, action_type, action_date_and_time)
                                                SELECT tmp_record_pair_id, OLD.id, OLD.currency, OLD.exchangeRate, OLD.defaultCurrency, OLD.basic_id, "B", "U",now();
			INSERT INTO expenses_tracker.h_currency_details(record_pair_id, id, currency, exchangeRate, defaultCurrency, basic_id, history_type, action_type, action_date_and_time)
			                                    SELECT tmp_record_pair_id, NEW.id, NEW.currency, NEW.exchangeRate, NEW.defaultCurrency, NEW.basic_id, "A", "U",now();
        ELSIF (TG_OP = "INSERT") THEN
			INSERT INTO expenses_tracker.h_currency_details(record_pair_id, id, currency, exchangeRate, defaultCurrency, basic_id, history_type, action_type, action_date_and_time) 
			                                    SELECT tmp_record_pair_id, NEW.id, NEW.currency, NEW.exchangeRate, NEW.defaultCurrency, NEW.basic_id, "A", "I",now();
        END IF;
        RETURN NULL; -- result is ignored since this is an AFTER trigger
    END;
' LANGUAGE plpgsql;

-- changeset ravindra.ambati:20240218004 labels:v2024.02.18 context:postgres
-- preconditions onFail:HALT onError:HALT
CREATE OR REPLACE TRIGGER t_currency_details
AFTER INSERT OR UPDATE OR DELETE ON expenses_tracker.currency_details
    FOR EACH ROW EXECUTE FUNCTION f_currency_details();