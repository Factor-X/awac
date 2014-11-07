-- Table: reducingaction

DROP TABLE IF EXISTS reducingaction;
DROP SEQUENCE IF EXISTS "reducingaction_id_seq";

CREATE SEQUENCE "reducingaction_id_seq";

CREATE TABLE reducingaction
(
  id bigint NOT NULL DEFAULT nextval('reducingaction_id_seq'::regclass),
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  comment text,
  completiondate timestamp without time zone,
  duedate timestamp without time zone,
  expectedpaybacktime character varying(255),
  financialbenefit double precision,
  ghgbenefit double precision,
  investmentcost double precision,
  physicalmeasure text,
  responsibleperson character varying(255),
  status character varying(1) NOT NULL,
  title character varying(255) NOT NULL,
  type character varying(1) NOT NULL,
  ghgbenefitunit_id bigint,
  scope_id bigint NOT NULL,
  website character varying(255),
  CONSTRAINT reducingaction_pkey PRIMARY KEY (id),
  CONSTRAINT fk_reducingaction_scope_id FOREIGN KEY (scope_id)
      REFERENCES scope (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_reducingaction_ghgbenefitunit_id FOREIGN KEY (ghgbenefitunit_id)
      REFERENCES unit (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_reducingaction_logical_pkey UNIQUE (title, scope_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE reducingaction
  OWNER TO play;


-- Table: reducingaction_storedfile

DROP TABLE IF EXISTS reducingaction_storedfile;

CREATE TABLE reducingaction_storedfile
(
  reducingaction_id bigint NOT NULL,
  storedfile_id bigint NOT NULL,
  CONSTRAINT fk_reducingaction_storedfile_storedfile_id FOREIGN KEY (storedfile_id)
      REFERENCES storedfile (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_reducingaction_storedfile_reducingaction_id FOREIGN KEY (reducingaction_id)
      REFERENCES reducingaction (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT uk_reducingaction_storedfile_logical_pkey UNIQUE (storedfile_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE reducingaction_storedfile
  OWNER TO play;
