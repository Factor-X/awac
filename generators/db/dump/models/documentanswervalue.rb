class Documentanswervalue < ActiveRecord::Base
    self.table_name = 'documentanswervalue'


    belongs_to :answer_value, :class_name => 'AnswerValue', :foreign_key => :id    
    belongs_to :storedfile, :class_name => 'Storedfile', :foreign_key => :storedfile_id    
end
