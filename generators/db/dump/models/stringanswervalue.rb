class Stringanswervalue < ActiveRecord::Base
    self.table_name = 'stringanswervalue'


    belongs_to :answer_value, :class_name => 'AnswerValue', :foreign_key => :id    
end
