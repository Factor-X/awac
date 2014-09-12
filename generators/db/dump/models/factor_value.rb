class FactorValue < ActiveRecord::Base
    self.table_name = 'factor_value'


    belongs_to :factor, :class_name => 'Factor', :foreign_key => :factor_id    
end
