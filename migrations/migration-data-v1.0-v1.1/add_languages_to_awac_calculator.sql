ALTER TABLE awaccalculator DROP COLUMN fr_enabled;
ALTER TABLE awaccalculator DROP COLUMN nl_enabled;
ALTER TABLE awaccalculator DROP COLUMN en_enabled;

INSERT INTO awaccalculator (code, creationdate, creationuser, lastupdatedate, lastupdateuser) values('household', localtimestamp, 'TECHNICAL', localtimestamp, 'TECHNICAL');
INSERT INTO awaccalculator (code, creationdate, creationuser, lastupdatedate, lastupdateuser) values('event', localtimestamp, 'TECHNICAL', localtimestamp, 'TECHNICAL');
INSERT INTO awaccalculator (code, creationdate, creationuser, lastupdatedate, lastupdateuser) values('littleEmitter', localtimestamp, 'TECHNICAL', localtimestamp, 'TECHNICAL');
INSERT INTO awaccalculator (code, creationdate, creationuser, lastupdatedate, lastupdateuser) values('verification', localtimestamp, 'TECHNICAL', localtimestamp, 'TECHNICAL');
INSERT INTO awaccalculator (code, creationdate, creationuser, lastupdatedate, lastupdateuser) values('admin', localtimestamp, 'TECHNICAL', localtimestamp, 'TECHNICAL');

ALTER TABLE awaccalculator ADD COLUMN fr_enabled BOOLEAN NULL;
ALTER TABLE awaccalculator ADD COLUMN nl_enabled BOOLEAN NULL;
ALTER TABLE awaccalculator ADD COLUMN en_enabled BOOLEAN NULL;

UPDATE awaccalculator SET fr_enabled=true;
UPDATE awaccalculator SET nl_enabled=false;
UPDATE awaccalculator SET en_enabled=false;

ALTER TABLE awaccalculator ALTER COLUMN fr_enabled SET NOT NULL;
ALTER TABLE awaccalculator ALTER COLUMN nl_enabled SET NOT NULL;
ALTER TABLE awaccalculator ALTER COLUMN en_enabled SET NOT NULL;






