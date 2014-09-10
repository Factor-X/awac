update mm_form_questionset set form_id = 4 where questionset_id = (select id from question_set where code = 'A205');
update mm_form_questionset set form_id = 4 where questionset_id = (select id from question_set where code = 'A208');
