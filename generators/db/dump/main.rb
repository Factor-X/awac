# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

# Rubygems
require 'rubygems'

require 'open-uri'

# Log
require_relative '../../_shared/util/log'

# == MAIN =================================================================== #

def main(options)

    # Initialize logger
    logger = Log.new('main')

    begin
        logger.info 'Dumping the database ...'

        require 'active_record'
        require 'pg'
        require 'awesome_print'
        require 'json'
        require 'ripl'

        ActiveRecord::Base.establish_connection(:adapter  => 'postgresql',
                                                :username => 'play',
                                                :password => 'play',
                                                :host     => 'localhost',
                                                :database => 'awac')

        Dir.glob(File.join(File.dirname(__FILE__), 'models', '*.rb')) { |f| require f }

        root = {
            :organizations => Organization.all.collect { |o| {
                :name     => o.name,
                :accounts => o.accounts.collect { |a| {
                    :identifier => a.identifier,
                    :person     => {
                        :email => a.person.email
                    }
                } },
                :sites    => o.sites.collect { |s| {
                    :name          => s.name,
                    :percent_owned => s.percent_owned
                } }
            } }
        }

        Ripl.start :binding => binding

        txt = JSON.pretty_generate(root, {:indent => ' ' * 4})

        for line in txt.split("\n")
            logger.info line
        end


        logger.info 'Done'
    rescue Exception => e
        logger.fatal e.message + "\n" + e.backtrace.collect { |l| (' '*55) + l }.join("\n")
    end
end


def inject_collection(host, key, collection)

    host[key] = {}
    for a in collection.attributes
        host[key][a] = collection
        end

    fot

end