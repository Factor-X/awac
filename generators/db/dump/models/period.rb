class Period < ActiveRecord::Base
    self.table_name = 'period'


    has_many :formprogresses, :class_name => 'Formprogress'    
    has_many :mm_site_periods, :class_name => 'MmSitePeriod'    
    has_many :questionsetanswers, :class_name => 'Questionsetanswer'    
end
