# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

# Rubygems
require 'rubygems'

# Code
require_relative 'util/code.rb'

# XLS Scanner
require_relative 'xls/scanner.rb'

# Survey writer
require_relative 'xls/survey_class_writer.rb'

# Question code writer
require_relative 'xls/question_code_writer.rb'

# YAML
require 'yaml'

# Logger
require_relative 'util/log.rb'

# == MAIN =================================================================== #

def main(options)

    # Load the configuration
    config            = YAML.load_file(File.dirname(__FILE__) + '/config.yaml')

    # Initialize logger
    Log.default_level = config['log_level']
    logger            = Log.new('main')

    begin
        logger.info 'The importer is starting...'

        for file in config['files']
            # Read and parse
            scanner = Scanner.new "#{ROOT}/data_importer_resources/#{file['xls']}", file['sheet']

            # Write the result
            SurveyClassWriter.execute file['name'], scanner.forms, scanner.question_sets, scanner.questions

            # Write the question codes
            codes = []
            codes += scanner.question_sets.collect { |qs| qs.accronym }
            codes += scanner.questions.collect { |q| q.accronym }
            QuestionCodeWriter.execute file['name'], codes
        end

    rescue Exception => e

        logger.fatal e

    end
end