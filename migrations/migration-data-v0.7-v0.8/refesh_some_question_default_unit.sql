update doublequestion set defaultunit_id = 24 where id = 89 or id = 90 or id = 91;
update doublequestion set defaultunit_id = 44 where id = 31;

-- remove convertion formula for Keur
delete from unitconversionformula where unit_id = 216;

-- add news
insert into unitconversionformula (unit_id, unittoreference,referencetounit,year) values
(216,'x*922.2114630','x*0.00108435',2000),
(216,'x*895.8085119','x*0.00111631',2001),
(216,'x*942.8447511','x*0.00106062',2002),
(216,'x*1129.8669016','x*0.00088506',2003),
(216,'x*1242.4675405','x*0.00080485',2004),
(216,'x*1243.3326287','x*0.00080429',2005),
(216,'x*1255.0515826','x*0.00079678',2006),
(216,'x*1368.7192893','x*0.00073061',2007),
(216,'x*1463.6788103','x*0.00068321',2008),
(216,'x*1390.9173099','x*0.00071895',2009),
(216,'x*1325.1351637','x*0.00075464',2010),
(216,'x*1391.2849908','x*0.00071876',2011),
(216,'x*1285.2479243','x*0.00077806',2012),
(216,'x*1327.7391258','x*0.00075316',2013);


delete from valueselectionquestion where id = 218;
insert into percentagequestion (id) values (218);

-- select * from question_answer, answer_value where question_id = 218 and answer_value.questionanswer_id = question_answer.id;

update answer_value a
set
	stringdata2 = null,
	stringdata1 = null,
	doubledata = 0.0,
	answertype = 'DOUBLE'
from question_answer q
where stringdata2 = '1' and a.questionanswer_id = q.id and q.question_id = 218;


update answer_value a
set
	stringdata2 = null,
	stringdata1 = null,
	doubledata = 0.1,
	answertype = 'DOUBLE'
from question_answer q
where stringdata2 = '2' and a.questionanswer_id = q.id and q.question_id = 218;


update answer_value a
set
	stringdata2 = null,
	stringdata1 = null,
	doubledata = 0.2,
	answertype = 'DOUBLE'
from question_answer q
where stringdata2 = '3' and a.questionanswer_id = q.id and q.question_id = 218;


update answer_value a
set
	stringdata2 = null,
	stringdata1 = null,
	doubledata = 0.3,
	answertype = 'DOUBLE'
from question_answer q
where stringdata2 = '4' and a.questionanswer_id = q.id and q.question_id = 218;


update answer_value a
set
	stringdata2 = null,
	stringdata1 = null,
	doubledata = 0.4,
	answertype = 'DOUBLE'
from question_answer q
where stringdata2 = '5' and a.questionanswer_id = q.id and q.question_id = 218;


update answer_value a
set
	stringdata2 = null,
	stringdata1 = null,
	doubledata = 0.5,
	answertype = 'DOUBLE'
from question_answer q
where stringdata2 = '6' and a.questionanswer_id = q.id and q.question_id = 218;


update answer_value a
set
	stringdata2 = null,
	stringdata1 = null,
	doubledata = 0.6,
	answertype = 'DOUBLE'
from question_answer q
where stringdata2 = '7' and a.questionanswer_id = q.id and q.question_id = 218;



update answer_value a
set
	stringdata2 = null,
	stringdata1 = null,
	doubledata = 0.7,
	answertype = 'DOUBLE'
from question_answer q
where stringdata2 = '8' and a.questionanswer_id = q.id and q.question_id = 218;



update answer_value a
set
	stringdata2 = null,
	stringdata1 = null,
	doubledata = 0.8,
	answertype = 'DOUBLE'
from question_answer q
where stringdata2 = '9' and a.questionanswer_id = q.id and q.question_id = 218;


update answer_value a
set
	stringdata2 = null,
	stringdata1 = null,
	doubledata = 0.9,
	answertype = 'DOUBLE'
from question_answer q
where stringdata2 = '10' and a.questionanswer_id = q.id and q.question_id = 218;



update answer_value a
set
	stringdata2 = null,
	stringdata1 = null,
	doubledata = 1.0,
	answertype = 'DOUBLE'
from question_answer q
where stringdata2 = '11' and a.questionanswer_id = q.id and q.question_id = 218;

