class Booleananswervalue < ActiveRecord::Base
    self.table_name = 'booleananswervalue'


    belongs_to :answer_value, :class_name => 'AnswerValue', :foreign_key => :id    
end
