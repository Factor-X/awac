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
        logger.info 'The importer is starting...'
        logger.warn 'Please be sure activator is up and ready to handle requests...'

        url = "http://127.0.0.1:9000/awac/admin/indicators_factors/reset"
        logger.info "Calling #{url} ... "
        open url

        logger.info 'Done'
    rescue Exception => e
        logger.fatal e.message + "\n" + e.backtrace.collect { |l| (' '*55) + l }.join("\n")
    end
end