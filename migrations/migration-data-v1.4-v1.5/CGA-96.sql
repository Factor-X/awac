insert into driver (name,creationuser,creationdate,lastupdatedate) values
('AM152','TECHNICAL',NOW(),NOW());

insert into drivervalue (creationuser,creationdate,lastupdatedate,from_period_id,defaultvalue,driver_id) values
('TECHNICAL',NOW(),NOW(),14,1265,(select id from driver where name = 'AM152'));

update doublequestion
set driver_id = (select id from driver where name = 'AM152')
from question as q
where doublequestion.id = q.id
and q.code = 'AM152';

