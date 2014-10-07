-- Column: active

ALTER TABLE organization DROP COLUMN IF EXISTS statistics_allowed;

ALTER TABLE organization ADD COLUMN statistics_allowed boolean;
ALTER TABLE organization ALTER COLUMN statistics_allowed SET DEFAULT true;