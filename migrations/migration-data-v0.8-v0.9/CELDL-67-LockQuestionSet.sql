
alter table question_answer drop column datalocker_id;
alter table question_answer drop column datavalidator_id;
alter table question_answer drop column dataverifier_id;
alter table question_answer drop column verificationstatus;

alter table questionsetanswer add column datalocker_id bigint;
alter table questionsetanswer add column datavalidator_id bigint;
alter table questionsetanswer add column dataverifier_id bigint;
alter table questionsetanswer add column verificationAsked boolean not null default false;

alter table questionsetanswer add  CONSTRAINT fk_7lx7y88jlce11ekknstfwqks1 FOREIGN KEY (datalocker_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

alter table questionsetanswer add  CONSTRAINT fk_olpuo127xgxdd8nybar62h57a FOREIGN KEY (datavalidator_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

alter table questionsetanswer add  CONSTRAINT fk_q1o2l7t9cegqlj6eh3gr8quqp FOREIGN KEY (dataverifier_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;



-- detach question_set from parent
select * from question_set where code = 'A93' or code = 'A113' or code = 'A163' or code  = 'A180' or code  = 'A130' or code  = 'A175';

update question_set set parent_id = null where code = 'A52' or code = 'A93' or code = 'A113' or code = 'A163' or code = 'A180' or code  = 'A130' or code  = 'A175';

-- link this questionset to the form
insert into mm_form_questionset (form_id,questionset_id) values (3,16),(3,25),(3,28),(4,33),(4,40),(5,46),(5,45);

-- and same for questionsetanswer
update questionsetanswer set parent_id = null from question_set where questionsetanswer.questionset_id = question_set.id and
(question_set.code = 'A52' or question_set.code = 'A93' or question_set.code = 'A113' or question_set.code = 'A163' or question_set.code = 'A180'  or question_set.code = 'A130' or question_set.code = 'A175');





