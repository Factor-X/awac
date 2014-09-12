DO $$

DECLARE
  answerValuesIds bigint[];
  answerValueId bigint;

BEGIN

  RAISE NOTICE 'add column ''answertype'' on ''answer_value'' table';

  ALTER TABLE answer_value ADD COLUMN answertype character varying(31);


  RAISE NOTICE 'set value of column ''answertype'' for existing answers';
  -- NOTE: documentanswervalue and entityanswervalue tables are empty => no need to check data

  answerValuesIds:=array(SELECT id FROM booleananswervalue);
  FOREACH answerValueId IN ARRAY answerValuesIds LOOP
    UPDATE answer_value SET answertype = 'BOOLEAN' where id = answerValueId;
  END LOOP;

  answerValuesIds:=array(SELECT id FROM codeanswervalue);
  FOREACH answerValueId IN ARRAY answerValuesIds LOOP
    UPDATE answer_value SET answertype = 'VALUE_SELECTION' where id = answerValueId;
  END LOOP;

  answerValuesIds:=array(SELECT id FROM doubleanswervalue);
  FOREACH answerValueId IN ARRAY answerValuesIds LOOP
    UPDATE answer_value SET answertype = 'DOUBLE' where id = answerValueId;
  END LOOP;

  answerValuesIds:=array(SELECT id FROM integeranswervalue);
  FOREACH answerValueId IN ARRAY answerValuesIds LOOP
    UPDATE answer_value SET answertype = 'INTEGER' where id = answerValueId;
  END LOOP;

  answerValuesIds:=array(SELECT id FROM stringanswervalue);
  FOREACH answerValueId IN ARRAY answerValuesIds LOOP
    UPDATE answer_value SET answertype = 'STRING' where id = answerValueId;
  END LOOP;


  RAISE NOTICE 'set column ''answertype'' not null';

  ALTER TABLE answer_value ALTER COLUMN answertype SET NOT NULL;


  RAISE NOTICE 'add column ''storedfile_id'' on ''answer_value'' table (and storedfile fk-constraint)';

  ALTER TABLE answer_value ADD COLUMN storedfile_id bigint;
  ALTER TABLE answer_value
    ADD CONSTRAINT fk_answer_value_storedfile_id FOREIGN KEY (storedfile_id)
      REFERENCES storedfile (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE;


  RAISE NOTICE 'drop ''booleananswervalue'', ''codeanswervalue'', ''doubleanswervalue'', ''integeranswervalue'', ''stringanswervalue'', ''documentanswervalue'' & ''entityanswervalue'' tables';
  DROP TABLE booleananswervalue, codeanswervalue, doubleanswervalue, integeranswervalue, stringanswervalue, documentanswervalue, entityanswervalue;

 
END $$;
