class Organization < ActiveRecord::Base
    self.table_name = 'organization'


    has_many :accounts, :class_name => 'Account'    
    has_many :scopes, :class_name => 'Scope'    
    has_many :sites, :class_name => 'Site'    
    has_many :products, :class_name => 'Product'    
    has_many :invitations, :class_name => 'Invitation'    
end
