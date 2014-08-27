BEGIN TRANSACTION READ WRITE;

DO $$

DECLARE
  tableNames varchar[] := ARRAY['account','person','organization','product','scope','site','codesequivalence','code_label','formprogress','answer_value',
    'questionsetanswer','question_set','form','factor','factor_value','indicator','period','unit','unit_category','unitconversionformula'];
  tableName varchar;

BEGIN
  FOREACH tableName IN ARRAY tableNames LOOP
    EXECUTE format('ALTER TABLE %I
           ADD COLUMN creationdate timestamp without time zone default (now() at time zone ''utc''),
           ADD COLUMN creationuser character varying(255),
           ADD COLUMN lastupdatedate timestamp without time zone,
	       ADD COLUMN lastupdateuser character varying(255)', tableName);
  END LOOP;

END $$;

COMMIT;
