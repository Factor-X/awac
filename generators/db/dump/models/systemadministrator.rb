class Systemadministrator < ActiveRecord::Base
    self.table_name = 'systemadministrator'


    belongs_to :account, :class_name => 'Account', :foreign_key => :id    
end
