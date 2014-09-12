class MmCalculatorIndicator < ActiveRecord::Base
    self.table_name = 'mm_calculator_indicator'


    belongs_to :indicator, :class_name => 'Indicator', :foreign_key => :indicator_id    
    belongs_to :awaccalculator, :class_name => 'Awaccalculator', :foreign_key => :calculator_id    
end
