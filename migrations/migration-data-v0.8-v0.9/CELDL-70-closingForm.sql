
-- create link between form and awacCalculator
alter table form add column awaccalculator_id bigint NULL;
alter table form add CONSTRAINT fk_qh4qn7byt91uv8e611s03aqav FOREIGN KEY (awaccalculator_id)
      REFERENCES awaccalculator (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE;

update form set awaccalculator_id = 1 where form.id = 2;
update form set awaccalculator_id = 1 where form.id = 3;
update form set awaccalculator_id = 1 where form.id = 4;
update form set awaccalculator_id = 1 where form.id = 5;
update form set awaccalculator_id = 1 where form.id = 6;
update form set awaccalculator_id = 1 where form.id = 7;

update form set awaccalculator_id = 2 where form.id = 8;
update form set awaccalculator_id = 2 where form.id = 9;
update form set awaccalculator_id = 2 where form.id = 10;
update form set awaccalculator_id = 2 where form.id = 11;
update form set awaccalculator_id = 2 where form.id = 12;


alter table form alter column awaccalculator_id set not null;

