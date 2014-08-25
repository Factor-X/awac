# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

require_relative '../util/log.rb'

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