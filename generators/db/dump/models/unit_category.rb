class UnitCategory < ActiveRecord::Base
    self.table_name = 'unit_category'


    belongs_to :unit, :class_name => 'Unit', :foreign_key => :mainunit_id    
    has_many :doublequestions, :class_name => 'Doublequestion'    
    has_many :integerquestions, :class_name => 'Integerquestion'    
    has_many :units, :class_name => 'Unit'    
    has_many :percentagequestions, :class_name => 'Percentagequestion'    
end
