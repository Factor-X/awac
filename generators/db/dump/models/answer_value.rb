class AnswerValue < ActiveRecord::Base
    self.table_name = 'answer_value'


    has_many :booleananswervalues, :class_name => 'Booleananswervalue', :foreign_key => :id    
    has_many :codeanswervalues, :class_name => 'Codeanswervalue', :foreign_key => :id    
    has_many :integeranswervalues, :class_name => 'Integeranswervalue', :foreign_key => :id    
    has_many :documentanswervalues, :class_name => 'Documentanswervalue', :foreign_key => :id    
    has_many :stringanswervalues, :class_name => 'Stringanswervalue', :foreign_key => :id    
    has_many :doubleanswervalues, :class_name => 'Doubleanswervalue', :foreign_key => :id    
    has_many :entityanswervalues, :class_name => 'Entityanswervalue', :foreign_key => :id    
    belongs_to :question_answer, :class_name => 'QuestionAnswer', :foreign_key => :questionanswer_id    
end
