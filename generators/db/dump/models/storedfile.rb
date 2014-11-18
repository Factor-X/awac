class Storedfile < ActiveRecord::Base
    self.table_name = 'storedfile'


    belongs_to :account, :class_name => 'Account', :foreign_key => :account_id    
    has_many :documentanswervalues, :class_name => 'Documentanswervalue'    
end
