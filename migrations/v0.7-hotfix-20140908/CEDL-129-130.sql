-- JIRA CELDL-129

delete from unitconversionformula where unittoreference = 'x*I';

-- JIRA CELDL-130

update unitconversionformula set unitToReference='9977637266L*x', referenceToUnit='X/9977637266L' where unit_id = (select id from unit where ref = 'U5144');
update unitconversionformula set unitToReference='3600000000L*x', referenceToUnit='X/3600000000L' where unit_id = (select id from unit where ref = 'U5337');
