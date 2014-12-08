# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

require_relative '../util/log.rb'

# == CODE =================================================================== #

class QuestionCodeWriter
    def self.execute(name, codes)

        @logger = Log.new(Code.for_static_class(self))

        infile  = ROOT + '/app/eu/factorx/awac/models/code/type/QuestionCode.java'
        outfile = infile
        begin
            @logger.info 'READING ' + infile
            source = IO.read(infile)

            lines = codes.sort { |a, b| a.match('([0-9]+)')[0].to_i <=> b.match('([0-9]+)')[0].to_i }.collect { |code| 'public static final QuestionCode ' + code+ ' = new QuestionCode("'+ code +'");' }

            begin_text = '/* BEGIN GENERATED QUESTION_CODES ' + Code.make(name) + ' */'
            end_text   = '/* END GENERATED QUESTION_CODES ' + Code.make(name) + ' */'
            comment    = '/* Generated the ' + Time.now.to_s + ' */'

            begin_index = source.index(begin_text)
            end_index   = source.index(end_text, begin_index)

            if begin_index == nil or end_index == nil
                raise Exception.new('Tag not found for ' + name + ' in ' + infile)
            end

            target = source[0..(begin_index + begin_text.length)] + "\n\n" + comment + "\n\n" + lines.join("\n") + "\n\n" + source[end_index..-1]

            File.open(outfile, 'w') do |f|
                f << target
            end

            @logger.info 'WRITTEN ' + outfile

        rescue Exception => e
            @logger.error 'Error in ' + infile + ' ' + e.message
            raise e
        end

    end
end