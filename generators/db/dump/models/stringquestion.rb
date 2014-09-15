class Stringquestion < ActiveRecord::Base
    self.table_name = 'stringquestion'


    belongs_to :question, :class_name => 'Question', :foreign_key => :id    
end
