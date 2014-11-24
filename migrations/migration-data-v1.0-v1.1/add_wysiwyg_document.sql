DROP TABLE IF EXISTS wysiwyg_document;
DROP SEQUENCE IF EXISTS "wysiwyg_document_id_seq";

CREATE SEQUENCE "wysiwyg_document_id_seq";

CREATE TABLE wysiwyg_document
(
  id bigint NOT NULL DEFAULT nextval('wysiwyg_document_id_seq'::regclass),
  creationdate timestamp without time zone,
  creationuser character varying(255),
  lastupdatedate timestamp without time zone,
  lastupdateuser character varying(255),
  content text NOT NULL,
  name character varying(255) NOT NULL,
  category character varying(255) NOT NULL,
  CONSTRAINT wysiwyg_document_pkey PRIMARY KEY (id),
   CONSTRAINT uk_wysiwyg_document_logical_pkey UNIQUE (category, name)
)
WITH (
  OIDS=FALSE
);


INSERT INTO wysiwyg_document (category, name, content) VALUES('agreement'       , 'fr'                      ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('agreement'       , 'nl'                      ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('agreement'       , 'en'                      ,'<p>Placez votre texte ici :-)</p>');

INSERT INTO wysiwyg_document (category, name, content) VALUES('confidentiality' , 'fr'                      ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('confidentiality' , 'nl'                      ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('confidentiality' , 'en'                      ,'<p>Placez votre texte ici :-)</p>');

INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'actions:fr'              ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'archive:fr'              ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'form:fr'                 ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'manage:fr'               ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'organization-manager:fr' ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'results:fr'              ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'site-manager:fr'         ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'submit:fr'               ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'user-data:fr'            ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'user-manager:fr'         ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'verification:fr'         ,'<p>Placez votre texte ici :-)</p>');

INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'actions:nl'              ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'archive:nl'              ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'form:nl'                 ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'manage:nl'               ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'organization-manager:nl' ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'results:nl'              ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'site-manager:nl'         ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'submit:nl'               ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'user-data:nl'            ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'user-manager:nl'         ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'verification:nl'         ,'<p>Placez votre texte ici :-)</p>');

INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'actions:en'              ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'archive:en'              ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'form:en'                 ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'manage:en'               ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'organization-manager:en' ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'results:en'              ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'site-manager:en'         ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'submit:en'               ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'user-data:en'            ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'user-manager:en'         ,'<p>Placez votre texte ici :-)</p>');
INSERT INTO wysiwyg_document (category, name, content) VALUES('help'            , 'verification:en'         ,'<p>Placez votre texte ici :-)</p>');
