-- Route AS_163 (GPL) to AS_2 (GPL) because AS_163 is now removed
update codesequivalence set codekey='AS_2' where codelist='CARBURANT' and codekey='AS_163';
update codesequivalence set referencedcodekey='AS_2' where codelist='CARBURANT' and referencedcodekey='AS_163';

-- Update the existing answers to reroute AS_163 to AS_2
update answer_value set stringdata2='AS_2' where answertype = 'VALUE_SELECTION' and stringdata1='CARBURANT' and stringdata2='AS_163';

-- finally remove the code_label
delete from code_label where codelist='ActivitySource' and key='AS_163'
