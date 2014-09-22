DROP TABLE IF EXISTS mm_site_period;

CREATE TABLE mm_site_period(
          site_id bigint NOT NULL,
          period_id bigint NOT NULL,
          CONSTRAINT fk_g9f4tnhyxrvp53v2vuc9r2m2k FOREIGN KEY (site_id)
              REFERENCES site (id) MATCH SIMPLE
              ON UPDATE NO ACTION ON DELETE CASCADE,
          CONSTRAINT fk_nn5ipj5ssh6ulm9ipliocbx5q FOREIGN KEY (period_id)
              REFERENCES period (id) MATCH SIMPLE
              ON UPDATE NO ACTION ON DELETE CASCADE
        )
WITH (
          OIDS=FALSE
     );

ALTER TABLE mm_site_period OWNER TO play;
