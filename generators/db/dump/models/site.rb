class Site < ActiveRecord::Base
    self.table_name = 'site'


    belongs_to :organization, :class_name => 'Organization', :foreign_key => :organization_id    
    has_many :mm_site_periods, :class_name => 'MmSitePeriod'    
    has_many :scopes, :class_name => 'Scope'    
end
