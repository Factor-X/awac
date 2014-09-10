DROP TABLE invitation;
DROP SEQUENCE "invitation_id_seq";

CREATE SEQUENCE "invitation_id_seq";

CREATE TABLE INVITATION
(
id bigint NOT NULL DEFAULT nextval('invitation_id_seq'::regclass),
organization_id bigint NOT NULL,
email varchar(255),
genkey varchar(255),
creationdate timestamp without time zone,
creationuser character varying(255),
lastupdatedate timestamp without time zone,
lastupdateuser character varying(255),
CONSTRAINT invitation_pkey PRIMARY KEY (id),
CONSTRAINT fk_organization FOREIGN KEY (organization_id)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

ALTER TABLE invitation OWNER TO play;