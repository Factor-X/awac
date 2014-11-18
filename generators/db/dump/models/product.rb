class Product < ActiveRecord::Base
    self.table_name = 'product'


    has_many :scopes, :class_name => 'Scope'    
    belongs_to :organization, :class_name => 'Organization', :foreign_key => :organization_id    
end
