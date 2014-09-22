-- used to remove the interface code from account and add it to the organization
-- with data migration


alter table organization add column interface_code character varying(255) null;

update organization set interface_code = account.interface_code from account where account.organization_id = organization.id;

alter table account drop column interface_code;

alter table organization alter column interface_code SET NOT NULL;