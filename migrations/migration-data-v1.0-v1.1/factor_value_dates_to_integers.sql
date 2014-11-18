alter table factor_value drop COLUMN dateOut;
alter table factor_value ADD COLUMN dateOut integer NULL;

alter table factor_value drop COLUMN dateIn;
alter table factor_value ADD COLUMN dateIn integer NULL;

