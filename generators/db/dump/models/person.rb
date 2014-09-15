class Person < ActiveRecord::Base
    self.table_name = 'person'


    has_many :accounts, :class_name => 'Account'    
end
