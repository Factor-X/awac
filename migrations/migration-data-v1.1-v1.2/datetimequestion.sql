DROP TABLE IF EXISTS datetimequestion;

-- Table: datetimequestion

DROP SEQUENCE IF EXISTS "datetimequestion_id_seq";

CREATE SEQUENCE "datetimequestion_id_seq";

CREATE TABLE datetimequestion
(
  id bigint NOT NULL DEFAULT nextval('datetimequestion_id_seq'::regclass),
  CONSTRAINT datetimequestion_pkey PRIMARY KEY (id),
  CONSTRAINT fk_datetimequestion_question_id FOREIGN KEY (id)
  REFERENCES question (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
  )
  WITH (
OIDS=FALSE
);
ALTER TABLE datetimequestion
OWNER TO play;

-- Table: answervalue - Column: datetime

ALTER TABLE answer_value DROP COLUMN IF EXISTS datetime;

ALTER TABLE answer_value ADD COLUMN datetime timestamp without time zone;

