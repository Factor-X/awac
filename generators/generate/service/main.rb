# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

# Rubygems
require 'rubygems'

# Logger
require_relative 'util/log.rb'

# == MAIN =================================================================== #

def main(options)


    Spreadsheet.client_encoding = 'UTF-8'

    # Initialize logger
    logger                      = Log.new('main')

    begin

        logger.info 'The importer is starting...'

        puts options

        logger.info 'Done'

    rescue Exception => e

        logger.fatal e.message + "\n" + e.backtrace.collect { |l| (' '*55) + l }.join("\n")

    end
end