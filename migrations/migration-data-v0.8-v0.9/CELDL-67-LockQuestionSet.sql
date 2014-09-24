
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

