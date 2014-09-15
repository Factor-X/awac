class Booleanquestion < ActiveRecord::Base
    self.table_name = 'booleanquestion'


    belongs_to :question, :class_name => 'Question', :foreign_key => :id    
end
