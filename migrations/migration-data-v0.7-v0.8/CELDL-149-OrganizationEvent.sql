-- Table: organizationevent

DROP TABLE IF EXISTS organizationevent;
DROP SEQUENCE IF EXISTS "organizationevent_id_seq";

CREATE SEQUENCE "organizationevent_id_seq";

CREATE TABLE organizationevent
(
  id bigint NOT NULL DEFAULT nextval('organizationevent_id_seq'::regclass),
  organization_id bigint NOT NULL,
  period_id bigint NOT NULL,
  name character varying(255) NOT NULL,
  description character varying(1000),
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),

  CONSTRAINT organizationevent_pkey PRIMARY KEY (id),
  CONSTRAINT fk_organizationevent_organization_id FOREIGN KEY (organization_id)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_organizationevent_period_id FOREIGN KEY (period_id)
      REFERENCES period (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_organizationevent_logical_pkey UNIQUE (organization_id, period_id, name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE organizationevent OWNER TO play;
