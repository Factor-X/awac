-- Column: orderindex

ALTER TABLE code_label DROP COLUMN IF EXISTS orderindex;

ALTER TABLE code_label ADD COLUMN orderindex integer;

-- Column: topic

ALTER TABLE code_label DROP COLUMN IF EXISTS topic;

ALTER TABLE code_label ADD COLUMN topic character varying(255);
