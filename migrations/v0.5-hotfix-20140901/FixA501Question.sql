-- see JIRA CELDL-110
﻿BEGIN TRANSACTION READ WRITE;

DO $$

DECLARE a501QuestionId integer;
DECLARE a201QuestionSetId integer;
DECLARE a501QuestionAnswerIds integer;
DECLARE a501QuestionAnswerId integer;
DECLARE periodId integer;
DECLARE scopeId integer;
DECLARE a201QuestionSetAnswerId integer;

BEGIN

-- Question A501

--   1. updating question A501: link to question set 'A201', instead of 'A194'
--     1.1.  get question A501 id
    select q.id from question as q where q.code = 'A501' into a501QuestionId;
--     1.2. get question set A201 id
    select qs.id from question_set qs where qs.code = 'A201' into a201QuestionSetId;
--     1.3. update question A501
    --update question set questionset_id = a201QuestionSetId where id = a501QuestionId;

--   2. updating question A501 answers: link to question set answer 'A201', instead of question set answer 'A194'

--     2.1. get question A501 answers ids
    a501QuestionAnswerIds:=(select qa.id from question_answer qa where qa.question_id = a501QuestionId);
--    2.2. for each question A501 answer...
    FOREACH a501QuestionAnswerId IN ARRAY a501QuestionAnswerIds LOOP
--       2.2.1. get scope and period ids
      select qsa.period_id from questionsetanswer qsa, question_answer qa where qsa.id = qa.questionsetanswer_id and qa.id = a501QuestionAnswerId into periodId;
      select qsa.period_id, qsa.scope_id from questionsetanswer qsa, question_answer qa where qsa.id = qa.questionsetanswer_id and qa.id = a501QuestionAnswerId into scopeId;
--       2.2.2. get correct question set answer linked to question set A201 (by scope & period) - this may return an empty result!
      select qsa.id from questionsetanswer qsa where qsa.period_id = periodId and qsa.scope_id = scopeId and qsa.questionset_id = a201QuestionSetId into a201QuestionSetAnswerId; 
--       2.2.3. update question answer linked to question A501: link to question set answer 'A201', instead of 'A194'
      -- update question_answer set questionsetanswer_id = a201QuestionSetAnswerId where id = a501QuestionAnswerId
    END LOOP;

END $$;

COMMIT;
