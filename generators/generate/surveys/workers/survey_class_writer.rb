# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

require 'rubygems'
require 'erubis'

require_relative '../util/log.rb'

require_relative '../models/form.rb'
require_relative '../models/question_set.rb'
require_relative '../models/question.rb'
require_relative '../models/bad.rb'

require_relative 'survey_class_writer_bindings.rb'



# == CODE =================================================================== #



class SurveyClassWriter

    def self.execute(name, forms, question_sets, questions)

        @logger = Log.new(Code.for_static_class(self))

        filename = File.dirname(__FILE__) + '/../templates/class.erb'
        input    = File.read(filename)
        eruby    = Erubis::Eruby.new(input)

        outfile = "#{ROOT}/app/eu/factorx/awac/generated/Awac#{name}InitialData.java"
        begin
            result = eruby.evaluate(SurveyClassWriterBindings.new(filename, {
                :name          => name,
                :forms         => forms,
                :question_sets => question_sets,
                :questions     => questions
            }))

            File.open(outfile, 'w') do |f|
                f << result
            end

            @logger.info 'WRITTEN ' + outfile

        rescue Exception => e
            @logger.error 'Error in ' + filename + ' ' + e.message
            raise e
        end

    end

end