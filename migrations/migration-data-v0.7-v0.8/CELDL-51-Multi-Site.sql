DROP TABLE USER_SITE_ASSOCIATION;
DROP SEQUENCE "user_site_association_id_seq";

CREATE SEQUENCE "user_site_association_id_seq";

CREATE TABLE USER_SITE_ASSOCIATION
(
id bigint NOT NULL DEFAULT nextval('user_site_association_id_seq'::regclass),
site_id bigint NOT NULL,
account_id bigint NOT NULL,
CONSTRAINT user_site_association_pkey PRIMARY KEY (id),
CONSTRAINT fk_site FOREIGN KEY (site_id)
      REFERENCES site (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
CONSTRAINT fk_account FOREIGN KEY (account_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

ALTER TABLE USER_SITE_ASSOCIATION OWNER TO play;

