class Account < ActiveRecord::Base
    self.table_name = 'account'


    has_many :question_answers, :class_name => 'QuestionAnswer'    
    has_many :systemadministrators, :class_name => 'Systemadministrator', :foreign_key => :id    
    has_many :question_answers, :class_name => 'QuestionAnswer'    
    has_many :question_answers, :class_name => 'QuestionAnswer'    
    has_many :storedfiles, :class_name => 'Storedfile'    
    belongs_to :account, :class_name => 'Account', :foreign_key => :id    
    has_many :question_answers, :class_name => 'QuestionAnswer'    
    belongs_to :person, :class_name => 'Person', :foreign_key => :person_id    
    belongs_to :organization, :class_name => 'Organization', :foreign_key => :organization_id    
end
