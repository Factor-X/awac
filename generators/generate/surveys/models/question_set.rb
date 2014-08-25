# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

# == CODE =================================================================== #

class QuestionSet
    attr_accessor :ref,
                  :accronym,
                  :text,
                  :repeatable,
                  :parent,
                  :form

    def validate
        if @parent == nil and @form == nil
            raise Exception.new "QuestionSet #{@accronym} should have a parent or a form but has neither."
        end
    end
end