update doublequestion set defaultunit_id = 24 where id = 89 or id = 90 or id = 91;
update doublequestion set defaultunit_id = 44 where id = 31;

-- remove convertion formula for Keur
delete from unitconversionformula where unit_id = 216;

-- add news
insert into unitconversionformula (unit_id, unittoreference,referencetounit,year) values
(216,'x*922.2114630','x*0.00108435',2000),
(216,'x*895.8085119','x*0.00111631',2001),
(216,'x*942.8447511','x*0.00106062',2002),
(216,'x*1129.8669016','x*0.00088506',2003),
(216,'x*1242.4675405','x*0.00080485',2004),
(216,'x*1243.3326287','x*0.00080429',2005),
(216,'x*1255.0515826','x*0.00079678',2006),
(216,'x*1368.7192893','x*0.00073061',2007),
(216,'x*1463.6788103','x*0.00068321',2008),
(216,'x*1390.9173099','x*0.00071895',2009),
(216,'x*1325.1351637','x*0.00075464',2010),
(216,'x*1391.2849908','x*0.00071876',2011),
(216,'x*1285.2479243','x*0.00077806',2012),
(216,'x*1327.7391258','x*0.00075316',2013);
