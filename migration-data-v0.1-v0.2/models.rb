class Organization
  attr_accessor :old_id,
                :old_name
  attr_accessor :ref_id
end

class Period
  attr_accessor :old_id,
                :old_label
  attr_accessor :ref_id
end

class AnswerValue
  attr_accessor :old_id,
                :old_booleandata,
                :old_doubledata,
                :old_longdata,
                :old_stringdata1,
                :old_stringdata2,
                :old_questionanswer_id
  attr_accessor :ref_id
end

class QuestionAnswer
  attr_accessor :old_id,
                :old_creationdate,
                :old_creationuser,
                :old_lastupdatedate,
                :old_lastupdateuser,
                :old_verificationstatus,
                :old_datalocker_id,
                :old_datavalidator_id,
                :old_dataverifier_id,
                :old_dataowner_id,
                :old_question_id,
                :old_questionsetanswer_id
  attr_accessor :ref_id
end

class Question
  attr_accessor :old_id,
                :old_creationdate,
                :old_creationuser,
                :old_lastupdatedate,
                :old_lastupdateuser,
                :old_code,
                :old_orderindex,
                :old_questionset_id
  attr_accessor :ref_id
end

class QuestionSetAnswer
  attr_accessor :old_id,
                :old_repetitionindex,
                :old_parent_id,
                :old_period_id,
                :old_questionset_id,
                :old_scope_id
  attr_accessor :ref_id
end

class Scope
  attr_accessor :old_id,
                :old_type,
                :old_organization_id,
                :old_product_id,
                :old_site_id
  attr_accessor :ref_id
end
