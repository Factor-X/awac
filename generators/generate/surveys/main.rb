# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

# Rubygems
require 'rubygems'

# Code
require_relative 'util/code.rb'

# XLS Scanner
require_relative 'workers/scanner.rb'

# Survey writer
require_relative 'workers/survey_class_writer.rb'

# Question code writer
require_relative 'workers/question_code_writer.rb'

# Code to import full writer
require_relative 'workers/codes_to_import_writer.rb'

# YAML
require 'yaml'

# Logger
require_relative 'util/log.rb'

# == MAIN =================================================================== #

def main(options)


    Spreadsheet.client_encoding = 'UTF-8'

    # Load the configuration
    config            = YAML.load_file(File.dirname(__FILE__) + '/config.yaml')

    # Initialize logger
    Log.default_level = config['log_level']
    logger            = Log.new('main')

    begin

        logger.info 'The importer is starting...'

        for file in config['files']

            logger.section 'Handling ' + file['name']

            # Read and parse
            logger.sub_section 'Parsing'
            scanner = Scanner.new "#{ROOT}/data_importer_resources/#{file['xls']}", file['sheet']

            # Write the result
            logger.sub_section 'Java class code generation'
            SurveyClassWriter.execute file['name'], scanner.forms, scanner.question_sets, scanner.questions

            # Write the question codes
            logger.sub_section 'Question codes injection'
            codes = []
            codes += scanner.question_sets.collect { |qs| qs.accronym }
            codes += scanner.questions.collect { |q| q.accronym }
            QuestionCodeWriter.execute file['name'], codes

            # Update the codes_to_import_full.xls
            logger.sub_section 'Codes_to_import_full generation'
            codes_to_import_writer = CodesToImportWriter.new file['name'], "#{ROOT}/data_importer_resources/#{file['xls']}", scanner.questions
            codes_to_import_writer.execute
        end

    rescue Exception => e

        logger.fatal e.message + "\n" + e.backtrace.collect { |l| (' '*45) + l }.join("\n")

    end
end