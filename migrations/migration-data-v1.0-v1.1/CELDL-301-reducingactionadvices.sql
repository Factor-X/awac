DROP TABLE IF EXISTS reducingactionadvice_baseindicator;
DROP TABLE IF EXISTS reducingactionadvice_storedfile;
DROP TABLE IF EXISTS reducingactionadvice;

-- Table: reducingactionadvice

DROP SEQUENCE IF EXISTS "reducingactionadvice_id_seq";

CREATE SEQUENCE "reducingactionadvice_id_seq";

CREATE TABLE reducingactionadvice
(
  id bigint NOT NULL DEFAULT nextval('reducingactionadvice_id_seq'::regclass),
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  comment text,
  expectedpaybacktime character varying(255),
  financialbenefit double precision,
  ghgbenefit double precision,
  investmentcost double precision,
  physicalmeasure text,
  responsibleperson character varying(255),
  title character varying(255) NOT NULL,
  website character varying(255),
  calculator_id bigint NOT NULL,
  ghgbenefitunit_id bigint,
  type character varying(1) NOT NULL,
  CONSTRAINT reducingactionadvice_pkey PRIMARY KEY (id),
  CONSTRAINT fk_reducingactionadvice_calculator_id FOREIGN KEY (calculator_id)
      REFERENCES awaccalculator (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_reducingactionadvice_ghgbenefitunit_id FOREIGN KEY (ghgbenefitunit_id)
      REFERENCES unit (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_reducingactionadvice_logical_pkey UNIQUE (title, calculator_id)
)
WITH (
  OIDS=FALSE
);

-- Table: reducingactionadvice_baseindicator

DROP SEQUENCE IF EXISTS "reducingactionadvice_baseindicator_id_seq";

CREATE SEQUENCE "reducingactionadvice_baseindicator_id_seq";

CREATE TABLE reducingactionadvice_baseindicator
(
  id bigint NOT NULL DEFAULT nextval('reducingactionadvice_baseindicator_id_seq'::regclass),
  percent double precision,
  actionadvice_id bigint NOT NULL,
  baseindicatorkey character varying(10) NOT NULL,
  CONSTRAINT reducingactionadvice_baseindicator_pkey PRIMARY KEY (id),
  CONSTRAINT fk_reducingactionadvice_baseindicator_actionadvice_id FOREIGN KEY (actionadvice_id)
  REFERENCES reducingactionadvice (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
OIDS=FALSE
);

-- Table: reducingactionadvice_storedfile

CREATE TABLE reducingactionadvice_storedfile
(
  reducingactionadvice_id bigint NOT NULL,
  storedfile_id bigint NOT NULL,
  CONSTRAINT fk_reducingactionadvice_storedfile_storedfile_id FOREIGN KEY (storedfile_id)
      REFERENCES storedfile (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_reducingactionadvice_storedfile_reducingactionadvice_id FOREIGN KEY (reducingactionadvice_id)
      REFERENCES reducingactionadvice (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_reducingactionadvice_storedfile_logical_pkey UNIQUE (storedfile_id)
)
WITH (
  OIDS=FALSE
);


