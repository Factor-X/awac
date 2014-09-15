class Form < ActiveRecord::Base
    self.table_name = 'form'


    has_many :mm_form_questionsets, :class_name => 'MmFormQuestionset'    
    has_many :formprogresses, :class_name => 'Formprogress'    
end
