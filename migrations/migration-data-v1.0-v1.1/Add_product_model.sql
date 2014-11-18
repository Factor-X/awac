DROP TABLE if exists  account_product_association;

DROP SEQUENCE if exists "account_product_association_id_seq";

CREATE SEQUENCE "account_product_association_id_seq";

CREATE TABLE account_product_association
(
id bigint NOT NULL DEFAULT nextval('account_product_association_id_seq'::regclass),
product_id bigint NOT NULL,
account_id bigint NOT NULL,
creationdate timestamp without time zone,
creationuser character varying(255),
lastupdatedate timestamp without time zone,
lastupdateuser character varying(255),
CONSTRAINT account_product_association_pkey PRIMARY KEY (id),
CONSTRAINT fk_product FOREIGN KEY (product_id)
      REFERENCES product (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
CONSTRAINT fk_account FOREIGN KEY (account_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

ALTER TABLE account_product_association OWNER TO play;

DROP TABLE IF EXISTS mm_product_period;

CREATE TABLE mm_product_period(
          product_id bigint NOT NULL,
          period_id bigint NOT NULL,
          CONSTRAINT fk_g9f4tnhyxrdp53v2vuc9r2m2k FOREIGN KEY (product_id)
              REFERENCES product (id) MATCH SIMPLE
              ON UPDATE NO ACTION ON DELETE CASCADE,
          CONSTRAINT fk_nn5ipj5ssh6ulm9ipliocbx5q FOREIGN KEY (period_id)
              REFERENCES period (id) MATCH SIMPLE
              ON UPDATE NO ACTION ON DELETE CASCADE
        )
WITH (
          OIDS=FALSE
     );

ALTER TABLE mm_product_period OWNER TO play;