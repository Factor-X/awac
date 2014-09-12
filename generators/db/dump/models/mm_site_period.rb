class MmSitePeriod < ActiveRecord::Base
    self.table_name = 'mm_site_period'


    belongs_to :period, :class_name => 'Period', :foreign_key => :period_id    
    belongs_to :site, :class_name => 'Site', :foreign_key => :site_id    
end
