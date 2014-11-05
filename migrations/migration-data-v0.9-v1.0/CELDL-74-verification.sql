
drop table if exists awaccalculatorclosed;

alter table questionsetanswer drop column verificationAsked;

CREATE SEQUENCE "awaccalculatorinstance_id_seq";


CREATE TABLE awaccalculatorinstance
(
  id bigserial NOT NULL,
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  closed boolean NOT NULL DEFAULT false,
  awaccalculator_id bigint NOT NULL,
  period_id bigint NOT NULL,
  scope_id bigint NOT NULL,
  CONSTRAINT awaccalculatorinstance_pkey PRIMARY KEY (id),
  CONSTRAINT fk_5ofemt9m0459dan4r9ne6qm1v FOREIGN KEY (scope_id)
      REFERENCES scope (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_7fhxfo2v5ns8gq4f2j7m3yfcs FOREIGN KEY (period_id)
      REFERENCES period (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_8hplmvn2i7m4u2m8di5yf3tfg FOREIGN KEY (awaccalculator_id)
      REFERENCES awaccalculator (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_eksc4nxguip7dgxqhk63ys99p UNIQUE (awaccalculator_id, period_id, scope_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE awaccalculatorinstance
  OWNER TO play;

CREATE SEQUENCE verificationrequest_id_seq;

  CREATE TABLE verificationrequest
(
 id bigserial NOT NULL,
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  content text,
  email character varying(255),
  phonenumber character varying(255),
  key character varying(255),
  verification_request_status character varying(255),
  awaccalculatorinstance_id bigint NOT NULL,
  contact_id bigint,
  organizationverifier_id bigint,
  verificationresultdocument_id bigint,
  verificationrejectedcomment character varying(65550),
  CONSTRAINT verificationrequest_pkey PRIMARY KEY (id),
  CONSTRAINT fk_o2y7uyf33tshgp8m9fv9l9y4n FOREIGN KEY (contact_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_qghch49rnv33lg97owp84sr1b FOREIGN KEY (organizationverifier_id)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_scqxhrkx1n9wjhuk3vm30emrw FOREIGN KEY (awaccalculatorinstance_id)
      REFERENCES awaccalculatorinstance (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_scqxhrkx1n9jehuk3vm30emrw FOREIGN KEY (verificationresultdocument_id)
      REFERENCES storedfile (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_scqxhrkx1n9wjhuk3vm30emrw UNIQUE (awaccalculatorinstance_id)
)
WITH (
  OIDS=FALSE
);


CREATE TABLE mm_verifierrequest_account
(
  verifier_id bigint NOT NULL,
  account_id bigint NOT NULL,
  CONSTRAINT fk_1dvxbpy2mi88qqy9p8ctwyp9e FOREIGN KEY (verifier_id)
      REFERENCES verificationrequest (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_7jvbk7bgrhh9gscfkrxk8ukir FOREIGN KEY (account_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mm_verifierrequest_account
  OWNER TO play;



ALTER TABLE verificationrequest
  OWNER TO play;

CREATE SEQUENCE verification_id_seq;

  CREATE TABLE verification
(
  id bigint NOT NULL DEFAULT nextval('verification_id_seq'::regclass),
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  comment text,
  verification_status character varying(255),
  questionsetanswer_id bigint,
  verificationrequest_id bigint,
  verifier_id bigint,
  CONSTRAINT verification_pkey PRIMARY KEY (id),
  CONSTRAINT fk_kykyp54mfexntis547fyt2q5x FOREIGN KEY (verifier_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_lii58oeg6xnbk0pavkgta7e91 FOREIGN KEY (verificationrequest_id)
      REFERENCES verificationrequest (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE ,
  CONSTRAINT fk_tp4mkpw6ctcy5vodebuc91ynk FOREIGN KEY (questionsetanswer_id)
      REFERENCES questionsetanswer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE verification
  OWNER TO play;


CREATE TABLE mm_storedfile_organization
(
  storedfile_id bigint NOT NULL,
  organization_id bigint NOT NULL,
  CONSTRAINT fk_1dkzbpy2ri88qqy9p8ctwyp9e FOREIGN KEY (storedfile_id)
      REFERENCES storedfile (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_7jvbk1borhh9gscfkrxk8ukir FOREIGN KEY (organization_id)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_mt4b8rprar8nmd6bkqops3odg UNIQUE (storedfile_id, organization_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mm_storedfile_organization
  OWNER TO play;




CREATE SEQUENCE verificationrequestcanceled_id_seq;

  CREATE TABLE verificationrequestcanceled
(
 id bigserial NOT NULL,
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  content text,
  email character varying(255),
  contact_id bigint,
  phonenumber character varying(255),
  awaccalculatorinstance_id bigint NOT NULL,
  organizationverifier_id bigint,
  CONSTRAINT verificationrequestcanceled_pkey PRIMARY KEY (id),
  CONSTRAINT fk_o2y7uyf33tshgpme9fv9l9y4n FOREIGN KEY (contact_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_qghch49rnv33lg97owp84sr1b FOREIGN KEY (organizationverifier_id)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_scqxhrkx1n9wjhuk3vje0emrw FOREIGN KEY (awaccalculatorinstance_id)
      REFERENCES awaccalculatorinstance (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE verificationrequestcanceled
  OWNER TO play;


alter table account add column is_main_verifier boolean not null default false;