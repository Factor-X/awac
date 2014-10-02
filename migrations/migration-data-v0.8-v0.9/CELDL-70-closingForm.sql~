
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

CREATE SEQUENCE awaccalculatorclosed_id_seq;

CREATE TABLE awaccalculatorclosed
(
  id bigserial  NOT NULL DEFAULT nextval('awaccalculator_id_seq'::regclass),
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  awaccalculator_id bigint NOT NULL,
  period_id bigint NOT NULL,
  scope_id bigint NOT NULL,
  CONSTRAINT awaccalculatorclosed_pkey PRIMARY KEY (id),
  CONSTRAINT fk_3ocihxvu8y9peg5vrdec07w8u FOREIGN KEY (period_id)
      REFERENCES period (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ifwewis0x6p1wk6esuph60svy FOREIGN KEY (awaccalculator_id)
      REFERENCES awaccalculator (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ll9558ynp5jwlbyl1a4u488fm FOREIGN KEY (scope_id)
      REFERENCES scope (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_778gostu9yt73w3v5k7lkvqer UNIQUE (awaccalculator_id, period_id, scope_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE awaccalculatorclosed
  OWNER TO play;


