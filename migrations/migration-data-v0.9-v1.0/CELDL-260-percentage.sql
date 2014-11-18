
select av.* from question_answer qa join percentagequestion q on q.id = qa.question_id join answer_value av on av.questionanswer_id = qa.id;


update answer_value as av
	set doubledata = doubledata / 100
from 	question_answer as qa,
	percentagequestion q
where av.questionanswer_id = qa.id and
	q.id = qa.question_id and
	av.lastupdatedate >= '2014-10-10';
