class Unitconversionformula < ActiveRecord::Base
    self.table_name = 'unitconversionformula'


    belongs_to :unit, :class_name => 'Unit', :foreign_key => :unit_id    
end
