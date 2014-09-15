class Indicator < ActiveRecord::Base
    self.table_name = 'indicator'

    self.inheritance_column = :ruby_type
    has_many :mm_calculator_indicators, :class_name => 'MmCalculatorIndicator'    
    belongs_to :unit, :class_name => 'Unit', :foreign_key => :unit_id    
end
