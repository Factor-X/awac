class QuestionAnswer < ActiveRecord::Base
    self.table_name = 'question_answer'


    belongs_to :account, :class_name => 'Account', :foreign_key => :dataowner_id    
    belongs_to :account, :class_name => 'Account', :foreign_key => :dataverifier_id    
    belongs_to :account, :class_name => 'Account', :foreign_key => :datavalidator_id    
    belongs_to :account, :class_name => 'Account', :foreign_key => :datalocker_id    
    has_many :answer_values, :class_name => 'AnswerValue'    
    belongs_to :questionsetanswer, :class_name => 'Questionsetanswer', :foreign_key => :questionsetanswer_id    
    belongs_to :question, :class_name => 'Question', :foreign_key => :question_id    
end
