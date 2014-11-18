class Invitation < ActiveRecord::Base
    self.table_name = 'invitation'


    belongs_to :organization, :class_name => 'Organization', :foreign_key => :organization_id    
end
