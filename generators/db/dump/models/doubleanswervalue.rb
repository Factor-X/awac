class Doubleanswervalue < ActiveRecord::Base
    self.table_name = 'doubleanswervalue'


    belongs_to :answer_value, :class_name => 'AnswerValue', :foreign_key => :id    
end
