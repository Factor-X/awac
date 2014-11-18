class Formprogress < ActiveRecord::Base
    self.table_name = 'formprogress'


    belongs_to :form, :class_name => 'Form', :foreign_key => :form    
    belongs_to :period, :class_name => 'Period', :foreign_key => :period    
    belongs_to :scope, :class_name => 'Scope', :foreign_key => :scope    
end
