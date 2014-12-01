ALTER TABLE unit_category ADD COLUMN mainFactorUnit_id BIGINT NULL;

UPDATE unit_category SET mainFactorUnit_id = mainunit_id;

ALTER TABLE unit_category ALTER COLUMN mainFactorUnit_id SET NOT NULL;

ALTER TABLE ONLY unit_category ADD CONSTRAINT mainFactorUnit_id FOREIGN KEY (mainFactorUnit_id) REFERENCES unit(id);
