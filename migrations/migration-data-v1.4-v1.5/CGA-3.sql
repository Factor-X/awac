


-- first : add column needed to account
alter table account add column email character varying(255);
alter table account add column   firstname character varying(255);
alter table account add column  lastname character varying(255);
alter table account add column  default_language character varying(2) NOT NULL DEFAULT 'FR'::character varying;

-- second : transfert data
update account
set firstname = person.firstname,
  lastname = person.lastname,
  email = person.email,
  default_language = person.default_language
from person
where person.id = person_id;

-- drop person_id column from account
alter table account drop column person_id;
alter table account alter column email set not null;

--clean this ... stuff
alter table account drop CONSTRAINT fk_account;

--drop person
drop table person ;



