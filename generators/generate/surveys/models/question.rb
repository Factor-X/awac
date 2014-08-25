# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

# == CODE =================================================================== #

class Question
    attr_accessor :ref,
                  :accronym,
                  :text,
                  :type,
                  :options,
                  :question_set,
                  :driver,
                  :unit_default

    def validate

        if @question_set == nil
            raise Exception.new "Question #{@accronym} has not question set."
        end

        if @type == 'MULTIPLE' and @options.to_s == ''
            raise Exception.new "Question #{@accronym} is of type #{@type} but has no associated list."
        end

        if @type.start_with? "UNIT_" and @unit_default.to_s == ''
            raise Exception.new "Question #{@accronym} is of type #{@type} but has no default unit."
        end


    end
end