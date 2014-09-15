class Entityanswervalue < ActiveRecord::Base
    self.table_name = 'entityanswervalue'


    belongs_to :answer_value, :class_name => 'AnswerValue', :foreign_key => :id    
end
