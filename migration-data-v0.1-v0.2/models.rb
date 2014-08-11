class Organization
  attr_accessor :id,
                :name
  attr_accessor :ref_id
end

class Period
  attr_accessor :id,
                :label
  attr_accessor :ref_id
end

class AnswerValue
  attr_accessor :id,
                :booleandata,
                :doubledata,
                :longdata,
                :stringdata1,
                :stringdata2,
                :questionanswer_id
  attr_accessor :ref_id
end

class QuestionAnswer
  attr_accessor :id,
                :creationdate,
                :creationuser,
                :lastupdatedate,
                :lastupdateuser,
                :verificationstatus,
                :datalocker_id,
                :datavalidator_id,
                :dataverifier_id,
                :dataowner_id,
                :question_id,
                :questionsetanswer_id
  attr_accessor :ref_id
end

class Question
  attr_accessor :id,
                :creationdate,
                :creationuser,
                :lastupdatedate,
                :lastupdateuser,
                :code,
                :orderindex,
                :questionset_id
  attr_accessor :ref_id
end


class QuestionSet
    attr_accessor :id,
                  :code,
                  :parent_id,
                  :repetitionallowed
    attr_accessor :ref_id
end

class QuestionSetAnswer
  attr_accessor :id,
                :repetitionindex,
                :parent_id,
                :period_id,
                :questionset_id,
                :scope_id
  attr_accessor :ref_id
end

class Scope
  attr_accessor :id,
                :type,
                :organization_id,
                :product_id,
                :site_id
  attr_accessor :ref_id
end
