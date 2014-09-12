class MmFormQuestionset < ActiveRecord::Base
    self.table_name = 'mm_form_questionset'


    belongs_to :form, :class_name => 'Form', :foreign_key => :form_id    
    belongs_to :question_set, :class_name => 'QuestionSet', :foreign_key => :questionset_id    
end
