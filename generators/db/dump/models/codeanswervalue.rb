class Codeanswervalue < ActiveRecord::Base
    self.table_name = 'codeanswervalue'


    belongs_to :answer_value, :class_name => 'AnswerValue', :foreign_key => :id    
end
