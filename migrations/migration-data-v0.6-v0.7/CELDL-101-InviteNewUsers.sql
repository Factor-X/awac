DROP TABLE invitation;
DROP SEQUENCE "invitation_id_seq";

CREATE SEQUENCE "invitation_id_seq";

CREATE TABLE INVITATION
(
id bigint NOT NULL DEFAULT nextval('invitation_id_seq'::regclass),
email varchar(255),
genkey varchar(255),
creationdate timestamp without time zone DEFAULT timezone('utc'::text, now()),
creationuser character varying(255),
lastupdatedate timestamp without time zone,
lastupdateuser character varying(255),
CONSTRAINT invitation_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

ALTER TABLE invitation OWNER TO play;