class Scope < ActiveRecord::Base
    self.table_name = 'scope'

    self.inheritance_column = :ruby_type
    belongs_to :product, :class_name => 'Product', :foreign_key => :product_id    
    has_many :formprogresses, :class_name => 'Formprogress'    
    has_many :questionsetanswers, :class_name => 'Questionsetanswer'    
    belongs_to :organization, :class_name => 'Organization', :foreign_key => :organization_id    
    belongs_to :site, :class_name => 'Site', :foreign_key => :site_id    
end
