update code_label set labelfr = 'Plus d''infos' where key = 'REDUCTION_ACTION_ADVICE_MORE_INFO';

INSERT INTO code_label (codelist, key, labelen, labelfr, labelnl, creationuser, lastupdateuser, orderindex, topic)
VALUES ('TRANSLATIONS_INTERFACE', 'REDUCTION_ACTION_ADVICE_MORE_INFO_HERE', 'here', 'ici', 'hier', 'TECHNICAL', 'TECHNICAL', (select (max(orderindex)+1) from code_label), 'actions');
