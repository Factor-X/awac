insert into mm_form_questionset (questionset_id,form_id)
values((select id as questionset_id from question_set where code = 'AP2'),(select id as form_id from form where identifier = 'TAB_P2'));