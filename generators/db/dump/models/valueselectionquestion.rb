class Valueselectionquestion < ActiveRecord::Base
    self.table_name = 'valueselectionquestion'


    belongs_to :question, :class_name => 'Question', :foreign_key => :id    
end
