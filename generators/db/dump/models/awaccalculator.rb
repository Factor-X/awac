class Awaccalculator < ActiveRecord::Base
    self.table_name = 'awaccalculator'


    has_many :mm_calculator_indicators, :class_name => 'MmCalculatorIndicator'    
end
