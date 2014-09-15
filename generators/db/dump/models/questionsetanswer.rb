class Questionsetanswer < ActiveRecord::Base
    self.table_name = 'questionsetanswer'


    belongs_to :period, :class_name => 'Period', :foreign_key => :period_id    
    belongs_to :questionsetanswer, :class_name => 'Questionsetanswer', :foreign_key => :parent_id    
    has_many :question_answers, :class_name => 'QuestionAnswer'    
    belongs_to :scope, :class_name => 'Scope', :foreign_key => :scope_id    
    belongs_to :question_set, :class_name => 'QuestionSet', :foreign_key => :questionset_id    
end
