-- see JIRA CELDL-110
﻿BEGIN TRANSACTION READ WRITE;

DO $$

DECLARE a195QuestionId integer;
DECLARE a197QuestionSetId integer;
DECLARE a195QuestionAnswerIds integer;
DECLARE a195QuestionAnswerId integer;
DECLARE periodId integer;
DECLARE scopeId integer;
DECLARE a197QuestionSetAnswerId integer;

BEGIN

-- Question A195

--   1. updating question A195: link to question set 'A197', instead of 'A194'
--     1.1.  get question A195 id
    select q.id from question as q where q.code = 'A195' into a195QuestionId;
--     1.2. get question set A197 id
    select qs.id from question_set qs where qs.code = 'A197' into a197QuestionSetId;
--     1.3. update question A195
    --update question set questionset_id = a197QuestionSetId where id = a195QuestionId;

--   2. updating question A195 answers: link to question set answer 'A197', instead of question set answer 'A194'

--     2.1. get question A195 answers ids
    a195QuestionAnswerIds:=(select qa.id from question_answer qa where qa.question_id = a195QuestionId);
--    2.2. for each question A195 answer...
    FOREACH a195QuestionAnswerId IN ARRAY a195QuestionAnswerIds LOOP
--       2.2.1. get scope and period ids
      select qsa.period_id from questionsetanswer qsa, question_answer qa where qsa.id = qa.questionsetanswer_id and qa.id = a195QuestionAnswerId into periodId;
      select qsa.period_id, qsa.scope_id from questionsetanswer qsa, question_answer qa where qsa.id = qa.questionsetanswer_id and qa.id = a195QuestionAnswerId into scopeId;
--       2.2.2. get correct question set answer linked to question set A197 (by scope & period) - this may return an empty result!
      select qsa.id from questionsetanswer qsa where qsa.period_id = periodId and qsa.scope_id = scopeId and qsa.questionset_id = a197QuestionSetId into a197QuestionSetAnswerId; 
--       2.2.3. update question answer linked to question A195: link to question set answer 'A197', instead of 'A194'
      -- update question_answer set questionsetanswer_id = a197QuestionSetAnswerId where id = a195QuestionAnswerId
    END LOOP;

END $$;

COMMIT;
