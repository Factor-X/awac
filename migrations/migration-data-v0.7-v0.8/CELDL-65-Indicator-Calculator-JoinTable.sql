
DROP TABLE IF EXISTS codeconversion;
DROP SEQUENCE IF EXISTS "codeconversion_id_seq";

CREATE SEQUENCE codeconversion_id_seq;

ALTER TABLE codeconversion_id_seq
  OWNER TO play;


CREATE TABLE codeconversion
(
  id bigserial NOT NULL,
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  codekey character varying(255) NOT NULL,
  codelist character varying(255) NOT NULL,
  conversioncriterion character varying(255) NOT NULL,
  referencedcodekey character varying(255) NOT NULL,
  CONSTRAINT codeconversion_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE codeconversion
  OWNER TO play;
