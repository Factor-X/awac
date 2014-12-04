insert into scope (id) values (1000);
insert into organization (id,name,interface_code) values (1000,'administrator', 'admin');

insert into account (organization_id,identifier,password,person_id,is_admin) values
(1000,'admin','3640622fc183ef1c7a1719f6fc4bd766b8fa01a5f4cf6442d5ce2a62f4f5852fe3825fc2485f469c',8,true);

