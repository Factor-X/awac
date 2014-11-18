class QuestionSet < ActiveRecord::Base
    self.table_name = 'question_set'


    has_many :mm_form_questionsets, :class_name => 'MmFormQuestionset'    
    has_many :questions, :class_name => 'Question'    
    belongs_to :question_set, :class_name => 'QuestionSet', :foreign_key => :parent_id    
    has_many :questionsetanswers, :class_name => 'Questionsetanswer'    
end
