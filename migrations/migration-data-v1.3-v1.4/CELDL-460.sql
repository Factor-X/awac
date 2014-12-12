DO $$

DECLARE
  oldIds bigint[];
  question_id_b bigint;
BEGIN

	oldIds:=array(select id from question where 
	--enterprise
	code = 'A12' or
	code = 'A410' or
	code = 'A510' or
	code = 'A610' or
	code = 'A228' or
	code = 'A242' or
	code = 'A337' or
	code = 'A338' or
	-- municipality
	code = 'AC410' or
	code = 'AC510' or
	code = 'AC610' or
	code = 'AC142' or
	code = 'AC143' or
	-- household
	code = 'AM115' or
	-- littmeemitter
	code = 'AP8' or
	code = 'AP76' or
	code = 'AP90' or
	code = 'AP98' or
	code = 'AP116' or
	code = 'AP131' or
	-- event
	code = 'AEV44' or
	code = 'AEV49' or
	code = 'AEV74' or
	code = 'AEV101');

	FOREACH question_id_b IN ARRAY oldIds LOOP
		insert into doublequestion (id) values (question_id_b);
		delete from integerquestion where id = question_id_b;
		update answer_value set answertype = 'DOUBLE' where questionanswer_id in ((select id from question_answer where question_id =  question_id_b));
	END LOOP;
	
END $$;