-- Table: awaccalculator

DROP TABLE IF EXISTS awaccalculator;
DROP SEQUENCE IF EXISTS "awaccalculator_id_seq";

CREATE SEQUENCE "awaccalculator_id_seq";

CREATE TABLE awaccalculator
(
  id bigint NOT NULL DEFAULT nextval('awaccalculator_id_seq'::regclass),
  code character varying(255),
  creationdate timestamp without time zone NOT NULL DEFAULT timezone('utc'::text, now()),
  creationuser character varying(255) NOT NULL DEFAULT 'TECHNICAL'::character varying,
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  CONSTRAINT awaccalculator_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE awaccalculator OWNER TO play;


-- Table: mm_calculator_indicator

DROP TABLE IF EXISTS mm_calculator_indicator;

CREATE TABLE mm_calculator_indicator
(
  calculator_id bigint NOT NULL,
  indicator_id bigint NOT NULL,
  CONSTRAINT fk_mm_calculator_indicator_calculator_id FOREIGN KEY (calculator_id)
      REFERENCES awaccalculator (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_mm_calculator_indicator_indicator_id FOREIGN KEY (indicator_id)
      REFERENCES indicator (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mm_calculator_indicator OWNER TO play;
