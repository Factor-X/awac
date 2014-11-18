-- Column: topic

-- ALTER TABLE code_label DROP COLUMN topic;

ALTER TABLE code_label ADD COLUMN topic character varying(255);
