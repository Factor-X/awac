-- change of NACE codes

DO $$

DECLARE
  answerValuesIds bigint[];
  answerValueId bigint;
  questionAnswerIds bigint[];
  questionAnswerId bigint;

BEGIN
 answerValuesIds:=array(select answer_value.id from answer_value,question_answer where answer_value.questionanswer_id=question_answer.id AND question_answer.question_id=5 AND answer_value.stringdata2 in ('5','6','7'));
 questionAnswerIds:=array(select question_answer.id from answer_value,question_answer where answer_value.questionanswer_id=question_answer.id AND question_answer.question_id=5 AND answer_value.stringdata2 in ('5','6','7'));

 FOREACH answerValueId IN ARRAY answerValuesIds LOOP
    -- delete from answer_value where id = answerValueId;
    DELETE FROM answer_value WHERE id=answerValueId;
 END LOOP;

 FOREACH questionAnswerId IN ARRAY questionAnswerIds LOOP
    -- delete from question_answer where id = questionAnswerId;
    DELETE FROM question_answer WHERE id=questionAnswerId;
 END LOOP;

END $$;