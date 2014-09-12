class Entityselectionquestion < ActiveRecord::Base
    self.table_name = 'entityselectionquestion'


    belongs_to :question, :class_name => 'Question', :foreign_key => :id    
end
