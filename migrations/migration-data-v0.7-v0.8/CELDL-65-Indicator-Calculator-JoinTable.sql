-- Table: awaccalculator

DROP TABLE awaccalculator;
DROP SEQUENCE "awaccalculator_id_seq";

CREATE SEQUENCE "awaccalculator_id_seq";

CREATE TABLE awaccalculator
(
  id bigint NOT NULL DEFAULT nextval('awaccalculator_id_seq'::regclass),
  creationdate timestamp without time zone DEFAULT timezone('utc'::text, now()),
  creationuser character varying(255) DEFAULT 'TECHNICAL'::character varying,
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  code character varying(255),
  CONSTRAINT awaccalculator_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE awaccalculator OWNER TO play;


-- Table: mm_calculator_indicator

DROP TABLE mm_calculator_indicator;

CREATE TABLE mm_calculator_indicator
(
  calculator_id bigint NOT NULL,
  indicator_id bigint NOT NULL,
  CONSTRAINT fk_6at3lg5o0qbvp3qh2nba9ropv FOREIGN KEY (calculator_id)
      REFERENCES awaccalculator (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_mlxk7iq56m38pkxk26onc4q5f FOREIGN KEY (indicator_id)
      REFERENCES indicator (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mm_calculator_indicator
  OWNER TO play;
