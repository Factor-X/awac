class Integeranswervalue < ActiveRecord::Base
    self.table_name = 'integeranswervalue'


    belongs_to :answer_value, :class_name => 'AnswerValue', :foreign_key => :id    
end
