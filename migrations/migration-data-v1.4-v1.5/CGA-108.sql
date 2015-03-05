-- 1) reducingactionadvice: add a percentmax column
ALTER TABLE reducingactionadvice_baseindicator ADD COLUMN percentmax double precision;

-- 2) reducingactionadvice: migrate existing advices (copies values of 'percent' column in 'percentmax' column)
update reducingactionadvice_baseindicator set percentmax = percent;

-- 3) reducingaction: add ghgbenefitmax & ghgbenefitmaxunit_id columns
ALTER TABLE reducingaction ADD COLUMN ghgbenefitmax double precision;
ALTER TABLE reducingaction ADD COLUMN ghgbenefitmaxunit_id bigint;

-- 4) reducingaction: migrate existing actions (copies values of 'ghgbenefit' & 'ghgbenefitunit_id' in 'ghgbenefitmax' & 'ghgbenefitmaxunit_id' columns)
update reducingaction set ghgbenefitmax = ghgbenefit, ghgbenefitmaxunit_id = ghgbenefitunit_id;

-- 5) modify fields translations (update ghgbenefit key, add ghgbenefitmax key)
update code_label set labelen = 'GHG Benefit (min)', labelfr = 'Gain CO2 (min)', labelnl = 'NL_Gain CO2 (min)' where key = 'REDUCTION_ACTION_GHG_BENEFIT_FIELD_TITLE';
update code_label set labelen = 'GHG Benefit (min)', labelfr = 'Gain minimum attendu en réduction d''émission de GES', labelnl = 'NL_Gain minimum attendu en réduction d''émission de GES' where key = 'REDUCTION_ACTION_GHG_BENEFIT_FIELD_PLACEHOLDER';
insert into code_label (codelist, key, labelen, labelfr, labelnl, creationuser, lastupdateuser, orderindex, topic)
 values ('TRANSLATIONS_INTERFACE', 'REDUCTION_ACTION_GHG_BENEFIT_MAX_FIELD_TITLE', 'GHG Benefit (max)', 'Gain CO2 (max)', 'NL_Gain CO2 (min)', 'TECHNICAL', 'TECHNICAL', 242, 'actions');
insert into code_label (codelist, key, labelen, labelfr, labelnl, creationuser, lastupdateuser, orderindex, topic)
 values ('TRANSLATIONS_INTERFACE', 'REDUCTION_ACTION_GHG_BENEFIT_MAX_FIELD_PLACEHOLDER', 'GHG Benefit (max)', 'Gain maximum attendu en réduction d''émission de GES', 'NL_Gain maximum attendu en réduction d''émission de GES', 'TECHNICAL', 'TECHNICAL', 243, 'actions');

-- 6) adapt error message translation
update code_label set labelen = 'Please define at least one reduction objective (providing a MAX value not equals to 0, and higher than MIN value).', labelfr = 'Veuillez définir au moins un objectif de réduction (avec une valeur MAX différente de 0 et supérieure à la valeur MIN).', labelnl = 'NL_Please define at least one reduction objective (providing a MAX value not equals to 0, and higher than MIN value).' where key = 'REDUCTION_ACTION_ADVICE_NO_REDUCTION_OBJECTIVES_ERROR';
