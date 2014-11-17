class Integerquestion < ActiveRecord::Base
    self.table_name = 'integerquestion'


    belongs_to :unit, :class_name => 'Unit', :foreign_key => :defaultunit_id    
    belongs_to :unit_category, :class_name => 'UnitCategory', :foreign_key => :unitcategory_id    
    belongs_to :question, :class_name => 'Question', :foreign_key => :id    
end
