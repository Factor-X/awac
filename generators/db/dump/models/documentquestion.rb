class Documentquestion < ActiveRecord::Base
    self.table_name = 'documentquestion'


    belongs_to :question, :class_name => 'Question', :foreign_key => :id    
end
