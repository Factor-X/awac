class Question < ActiveRecord::Base
    self.table_name = 'question'


    has_many :doublequestions, :class_name => 'Doublequestion', :foreign_key => :id    
    has_many :integerquestions, :class_name => 'Integerquestion', :foreign_key => :id    
    has_many :valueselectionquestions, :class_name => 'Valueselectionquestion', :foreign_key => :id    
    has_many :documentquestions, :class_name => 'Documentquestion', :foreign_key => :id    
    has_many :booleanquestions, :class_name => 'Booleanquestion', :foreign_key => :id    
    has_many :entityselectionquestions, :class_name => 'Entityselectionquestion', :foreign_key => :id    
    has_many :stringquestions, :class_name => 'Stringquestion', :foreign_key => :id    
    has_many :percentagequestions, :class_name => 'Percentagequestion', :foreign_key => :id    
    has_many :question_answers, :class_name => 'QuestionAnswer'    
    belongs_to :question_set, :class_name => 'QuestionSet', :foreign_key => :questionset_id    
end
