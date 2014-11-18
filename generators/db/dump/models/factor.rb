class Factor < ActiveRecord::Base
    self.table_name = 'factor'


    has_many :factor_values, :class_name => 'FactorValue'    
    belongs_to :unit, :class_name => 'Unit', :foreign_key => :unitin_id    
    belongs_to :unit, :class_name => 'Unit', :foreign_key => :unitout_id    
end
