
update formprogress set scope = o.id from organization as o, site as s where o.interface_code = 'municipality' and s.organization_id = o.id and formprogress.scope = s.id;

update questionsetanswer set scope_id = o.id from organization as o, site as s where o.interface_code = 'municipality' and s.organization_id = o.id and questionsetanswer.scope_id = s.id;

delete from account_site_association using organization, site where organization.id = site.organization_id and organization.interface_code = 'municipality' and account_site_association.site_id = site.id;
delete from site using organization where organization.id = site.organization_id and organization.interface_code = 'municipality';
