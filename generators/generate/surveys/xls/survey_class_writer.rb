# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

require 'rubygems'
require 'erubis'

require_relative '../models/form.rb'
require_relative '../models/question_set.rb'
require_relative '../models/question.rb'
require_relative '../models/bad.rb'


# == CODE =================================================================== #

class SurveyClassWriterBindings

    attr_accessor :hash

    def initialize(filename, hash)
        @filename = filename
        @hash     = hash
    end

    def partial(o)
        @logger = Log.new 'SurveyClassWriter'

        filename = File.dirname(@filename) + '/' + o[:template] + '.erb'
        input    = File.read(filename)
        eruby    = Erubis::Eruby.new(input)

        begin
            eruby.evaluate(SurveyClassWriterBindings.new(filename, o[:locals]))
        rescue Exception => e
            @logger.error 'Error in ' + filename + ' ' + e.message
            raise e
        end
    end

    def method_missing(name, *args, &block)
        if args.empty?
            @hash[name.to_sym]
        else
            @hash[name.to_sym] = args[0]
        end
    end

end


class SurveyClassWriter

    def self.execute(name, forms, question_sets, questions)

        @logger = Log.new(Code.for_static_class(self))

        filename = File.dirname(__FILE__) + '/../templates/class.erb'
        input    = File.read(filename)
        eruby    = Erubis::Eruby.new(input)

        outfile = ROOT + '/app/eu/factorx/awac/generated/Awac' + name + 'InitialData.java'
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