
CREATE SEQUENCE "driver_id_seq";


CREATE TABLE driver
(
  id bigint NOT NULL DEFAULT nextval('driver_id_seq'::regclass),
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  name character varying(255),

  CONSTRAINT driver_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE driver
  OWNER TO play;


CREATE SEQUENCE "drivervalue_id_seq";

CREATE TABLE drivervalue
(
  id bigint NOT NULL DEFAULT nextval('drivervalue_id_seq'::regclass),
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  from_period_id bigint NOT NULL,
  defaultvalue double precision NOT NULL,
   driver_id  bigint NOT NULL,

  CONSTRAINT drivervalue_pkey PRIMARY KEY (id),
  CONSTRAINT fk_from_period_id FOREIGN KEY (from_period_id)
      REFERENCES period (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_driver_id2 FOREIGN KEY (driver_id)
      REFERENCES driver (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE drivervalue
  OWNER TO play;






 -- update data

 -- percentage
 alter table percentagequestion drop column defaultvalue;
 alter table percentagequestion add column driver_id bigint null;
 alter table percentagequestion add CONSTRAINT fk_siodkpsokfposdfk FOREIGN KEY (driver_id)
      REFERENCES driver (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE;

-- data
insert into driver (id,name) values (1,'Taux de recyclé');
insert into drivervalue (driver_id,defaultvalue,from_period_id) values (1,0.9,14);
update percentagequestion set driver_id = 1 where id = 333;

-- double

 alter table doublequestion drop column defaultvalue;
 alter table doublequestion add column driver_id bigint null;
 alter table doublequestion add CONSTRAINT fk_siodkpsokfpo5sdfk FOREIGN KEY (driver_id)
      REFERENCES driver (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE;

-- integer

 alter table integerquestion drop column defaultvalue;
 alter table integerquestion add column driver_id bigint null;
 alter table integerquestion add CONSTRAINT fk_siodkps8okfpo5sdfk FOREIGN KEY (driver_id)
      REFERENCES driver (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE;

-- data
insert into driver (id,name) values (2,'Nombre de jours de travail/an');
insert into drivervalue (driver_id,defaultvalue,from_period_id) values (2,220,14);
update integerquestion set driver_id = 2 where id = 127;
update integerquestion set driver_id = 2 where id = 129;
update integerquestion set driver_id = 2 where id = 133;


insert into driver (id,name) values (3,'Nombre de jours d''ouverture/an');
insert into drivervalue (driver_id,defaultvalue,from_period_id) values (3,365,14);
update integerquestion set driver_id = 3 where id = 131;


