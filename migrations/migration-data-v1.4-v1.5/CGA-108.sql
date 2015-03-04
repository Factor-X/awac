-- Adds a 'percent_max' column
ALTER TABLE reducingactionadvice_baseindicator ADD COLUMN percentmax double precision;

-- Copies values of 'percent' column in 'percentmax' column
update reducingactionadvice_baseindicator set percentmax = percent;
