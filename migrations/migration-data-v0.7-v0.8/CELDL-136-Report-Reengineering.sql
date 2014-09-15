-- CLEAN UP

DROP TABLE IF EXISTS mm_calculator_indicator;

DROP TABLE IF EXISTS mm_indicator_baseindicator;

DROP TABLE IF EXISTS mm_report_indicator;
DROP SEQUENCE IF EXISTS mm_report_indicator_id_seq;

DROP TABLE IF EXISTS baseindicator;
DROP SEQUENCE IF EXISTS baseindicator_id_seq;

DROP TABLE IF EXISTS indicator;
DROP SEQUENCE IF EXISTS indicator_id_seq;

DROP TABLE IF EXISTS report;
DROP SEQUENCE IF EXISTS report_id_seq;

DROP TABLE IF EXISTS awaccalculator;
DROP SEQUENCE IF EXISTS awaccalculator_id_seq;


-- Table: awaccalculator

CREATE SEQUENCE "awaccalculator_id_seq";
CREATE TABLE awaccalculator
(
  id bigint NOT NULL DEFAULT nextval('awaccalculator_id_seq'::regclass),
  code character varying(255),
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  CONSTRAINT awaccalculator_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE awaccalculator
  OWNER TO play;

  

-- Table: baseindicator


CREATE SEQUENCE "baseindicator_id_seq";
CREATE TABLE baseindicator
(
  id bigint NOT NULL DEFAULT nextval('baseindicator_id_seq'::regclass),
  key character varying(255),
  name character varying(255),
  activitycategory character varying(255),
  activityownership boolean,
  activitysubcategory character varying(255),
  deleted boolean,
  indicatorcategory character varying(255),
  scope_iso character varying(255),
  scopetype character varying(255),
  type character varying(255),
  unit_id bigint,
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  CONSTRAINT baseindicator_pkey PRIMARY KEY (id),
  CONSTRAINT fk_543ri5xkhpshym6b4c3x4rmsj FOREIGN KEY (unit_id)
      REFERENCES unit (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_i22pdwc7w93s12qti3kkfp8oc UNIQUE (key)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE baseindicator
  OWNER TO play;



-- Table: indicator

CREATE SEQUENCE "indicator_id_seq";
CREATE TABLE indicator
(
  id bigint NOT NULL DEFAULT nextval('indicator_id_seq'::regclass),
  code character varying(255),
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  CONSTRAINT indicator_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE indicator
  OWNER TO play;


-- Table: report

CREATE SEQUENCE "report_id_seq";
CREATE TABLE report
(
  id bigint NOT NULL DEFAULT nextval('report_id_seq'::regclass),
  code character varying(255),
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  awaccalculator_id bigint NOT NULL,
  CONSTRAINT report_pkey PRIMARY KEY (id),
  CONSTRAINT fk_report_awaccalculator_id FOREIGN KEY (awaccalculator_id)
    REFERENCES awaccalculator (id) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE report
  OWNER TO play;

  
  
-- Table: mm_report_indicator

CREATE SEQUENCE "mm_report_indicator_id_seq";
CREATE TABLE mm_report_indicator
(
  id bigint NOT NULL DEFAULT nextval('mm_report_indicator_id_seq'::regclass),
  orderindex integer NOT NULL,
  indicator_id bigint NOT NULL,
  report_id bigint NOT NULL,
  CONSTRAINT mm_report_indicator_pkey PRIMARY KEY (id),
  CONSTRAINT fk_mm_report_indicator_report_id FOREIGN KEY (report_id)
      REFERENCES report (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_mm_report_indicator_indicator_id FOREIGN KEY (indicator_id)
      REFERENCES indicator (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mm_report_indicator
  OWNER TO play;
  
  
-- Table: mm_indicator_baseindicator

CREATE TABLE mm_indicator_baseindicator
(
  indicator_id bigint NOT NULL,
  baseindicator_id bigint NOT NULL,
  CONSTRAINT fk_c0w7dcujq7dcw2b7xuoc76xlx FOREIGN KEY (indicator_id)
      REFERENCES indicator (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_flgffvmhciyr38nbbobikvvf2 FOREIGN KEY (baseindicator_id)
      REFERENCES baseindicator (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mm_indicator_baseindicator
  OWNER TO play;