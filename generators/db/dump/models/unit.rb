class Unit < ActiveRecord::Base
    self.table_name = 'unit'


    has_many :factors, :class_name => 'Factor'    
    has_many :indicators, :class_name => 'Indicator'    
    has_many :factors, :class_name => 'Factor'    
    has_many :percentagequestions, :class_name => 'Percentagequestion'    
    has_many :unit_categories, :class_name => 'UnitCategory'    
    has_many :unitconversionformulas, :class_name => 'Unitconversionformula'    
    has_many :doublequestions, :class_name => 'Doublequestion'    
    has_many :integerquestions, :class_name => 'Integerquestion'    
    belongs_to :unit_category, :class_name => 'UnitCategory', :foreign_key => :category_id    
end
