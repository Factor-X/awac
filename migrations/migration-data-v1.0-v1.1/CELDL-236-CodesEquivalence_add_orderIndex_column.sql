-- Column: orderindex

ALTER TABLE codesequivalence DROP COLUMN IF EXISTS orderindex;

ALTER TABLE codesequivalence ADD COLUMN orderindex integer;
