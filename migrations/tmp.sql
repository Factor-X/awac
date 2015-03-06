CREATE INDEX idx_answer_value_comment on answer_value using btree(comment);
CREATE INDEX idx_answer_value_stringdata1 on answer_value using btree(stringdata1);
CREATE INDEX idx_answer_value_stringdata2 on answer_value using btree(stringdata2);
CREATE INDEX idx_answer_value_answertype on answer_value using btree(answertype);
CREATE INDEX idx_awaccalculator_code on awaccalculator using btree(code);
CREATE INDEX idx_code_label_codelist on code_label using btree(codelist);
CREATE INDEX idx_code_label_key on code_label using btree(key);
CREATE INDEX idx_code_label_topic on code_label using btree(topic);
CREATE INDEX idx_codeconversion_codekey on codeconversion using btree(codekey);
CREATE INDEX idx_codeconversion_codelist on codeconversion using btree(codelist);
CREATE INDEX idx_codeconversion_conversioncriterion on codeconversion using btree(conversioncriterion);
CREATE INDEX idx_codeconversion_referencedcodekey on codeconversion using btree(referencedcodekey);
CREATE INDEX idx_codesequivalence_codekey on codesequivalence using btree(codekey);
CREATE INDEX idx_codesequivalence_codelist on codesequivalence using btree(codelist);
CREATE INDEX idx_codesequivalence_referencedcodekey on codesequivalence using btree(referencedcodekey);
CREATE INDEX idx_codesequivalence_referencedcodelist on codesequivalence using btree(referencedcodelist);
CREATE INDEX idx_form_identifier on form using btree(identifier);
CREATE INDEX idx_driver_name on driver using btree(name);
CREATE INDEX idx_factor_activitysource on factor using btree(activitysource);
CREATE INDEX idx_factor_activitytype on factor using btree(activitytype);
CREATE INDEX idx_factor_indicatorcategory on factor using btree(indicatorcategory);
CREATE INDEX idx_factor_institution on factor using btree(institution);
CREATE INDEX idx_factor_key on factor using btree(key);
CREATE INDEX idx_entityselectionquestion_entityname on entityselectionquestion using btree(entityname);
CREATE INDEX idx_product_name on product using btree(name);
CREATE INDEX idx_period_label on period using btree(label);
CREATE INDEX idx_period_code on period using btree(code);
CREATE INDEX idx_question_code on question using btree(code);
CREATE INDEX idx_indicator_code on indicator using btree(code);
CREATE INDEX idx_indicator_activitycategory on indicator using btree(activitycategory);
CREATE INDEX idx_indicator_activitysubcategory on indicator using btree(activitysubcategory);
CREATE INDEX idx_indicator_indicatorcategory on indicator using btree(indicatorcategory);
CREATE INDEX idx_indicator_scope_iso on indicator using btree(scope_iso);
CREATE INDEX idx_indicator_key on indicator using btree(key);
CREATE INDEX idx_indicator_name on indicator using btree(name);
CREATE INDEX idx_indicator_scopetype on indicator using btree(scopetype);
CREATE INDEX idx_indicator_type on indicator using btree(type);
CREATE INDEX idx_organization_name on organization using btree(name);
CREATE INDEX idx_organization_description on organization using btree(description);
CREATE INDEX idx_organization_nacecode on organization using btree(nacecode);
CREATE INDEX idx_organization_interface_code on organization using btree(interface_code);
CREATE INDEX idx_organizationevent_name on organizationevent using btree(name);
CREATE INDEX idx_organizationevent_description on organizationevent using btree(description);
CREATE INDEX idx_invitation_email on invitation using btree(email);
CREATE INDEX idx_invitation_genkey on invitation using btree(genkey);
CREATE INDEX idx_reducingactionadvice_baseindicator_baseindicatorkey on reducingactionadvice_baseindicator using btree(baseindicatorkey);
CREATE INDEX idx_stringquestion_defaultvalue on stringquestion using btree(defaultvalue);
CREATE INDEX idx_reducingactionadvice_expectedpaybacktime on reducingactionadvice using btree(expectedpaybacktime);
CREATE INDEX idx_reducingactionadvice_responsibleperson on reducingactionadvice using btree(responsibleperson);
CREATE INDEX idx_reducingactionadvice_title on reducingactionadvice using btree(title);
CREATE INDEX idx_reducingactionadvice_website on reducingactionadvice using btree(website);
CREATE INDEX idx_reducingactionadvice_type on reducingactionadvice using btree(type);
CREATE INDEX idx_reducingaction_expectedpaybacktime on reducingaction using btree(expectedpaybacktime);
CREATE INDEX idx_reducingaction_responsibleperson on reducingaction using btree(responsibleperson);
CREATE INDEX idx_reducingaction_status on reducingaction using btree(status);
CREATE INDEX idx_reducingaction_title on reducingaction using btree(title);
CREATE INDEX idx_reducingaction_type on reducingaction using btree(type);
CREATE INDEX idx_reducingaction_website on reducingaction using btree(website);
CREATE INDEX idx_report_code on report using btree(code);
CREATE INDEX idx_report_retricted_scope on report using btree(retricted_scope);
CREATE INDEX idx_site_name on site using btree(name);
CREATE INDEX idx_site_accounting_treatment on site using btree(accounting_treatment);
CREATE INDEX idx_site_economic_interest on site using btree(economic_interest);
CREATE INDEX idx_site_nace_code on site using btree(nace_code);
CREATE INDEX idx_site_operating_policy on site using btree(operating_policy);
CREATE INDEX idx_site_organizational_structure on site using btree(organizational_structure);
CREATE INDEX idx_verificationrequestcanceled_email on verificationrequestcanceled using btree(email);
CREATE INDEX idx_verificationrequestcanceled_phonenumber on verificationrequestcanceled using btree(phonenumber);
CREATE INDEX idx_unit_category_name on unit_category using btree(name);
CREATE INDEX idx_unit_category_ref on unit_category using btree(ref);
CREATE INDEX idx_unit_category_symbol on unit_category using btree(symbol);
CREATE INDEX idx_unitconversionformula_referencetounit on unitconversionformula using btree(referencetounit);
CREATE INDEX idx_unitconversionformula_unittoreference on unitconversionformula using btree(unittoreference);
CREATE INDEX idx_verification_verification_status on verification using btree(verification_status);
CREATE INDEX idx_wysiwyg_document_name on wysiwyg_document using btree(name);
CREATE INDEX idx_wysiwyg_document_category on wysiwyg_document using btree(category);
CREATE INDEX idx_valueselectionquestion_codelist on valueselectionquestion using btree(codelist);
CREATE INDEX idx_question_set_code on question_set using btree(code);
CREATE INDEX idx_baseindicator_code on baseindicator using btree(code);
CREATE INDEX idx_baseindicator_activitycategory on baseindicator using btree(activitycategory);
CREATE INDEX idx_baseindicator_activitysubcategory on baseindicator using btree(activitysubcategory);
CREATE INDEX idx_baseindicator_indicatorcategory on baseindicator using btree(indicatorcategory);
CREATE INDEX idx_baseindicator_scope_iso on baseindicator using btree(scope_iso);
CREATE INDEX idx_baseindicator_scopetype on baseindicator using btree(scopetype);
CREATE INDEX idx_baseindicator_type on baseindicator using btree(type);
CREATE INDEX idx_verificationrequest_email on verificationrequest using btree(email);
CREATE INDEX idx_verificationrequest_phonenumber on verificationrequest using btree(phonenumber);
CREATE INDEX idx_verificationrequest_key on verificationrequest using btree(key);
CREATE INDEX idx_verificationrequest_verification_request_status on verificationrequest using btree(verification_request_status);
CREATE INDEX idx_verificationrequest_verificationrejectedcomment on verificationrequest using btree(verificationrejectedcomment);
CREATE INDEX idx_account_identifier_password on account using btree(identifier,password);
CREATE INDEX idx_storedfile_originalname on storedfile using btree(originalname);
CREATE INDEX idx_storedfile_storedname on storedfile using btree(storedname);
CREATE INDEX idx_unit_name on unit using btree(name);
CREATE INDEX idx_unit_ref on unit using btree(ref);
CREATE INDEX idx_unit_symbol on unit using btree(symbol);
