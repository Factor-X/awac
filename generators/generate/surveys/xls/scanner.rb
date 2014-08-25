# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

require 'rubygems'
require 'spreadsheet'

require_relative '../models/form.rb'
require_relative '../models/question_set.rb'
require_relative '../models/question.rb'
require_relative '../models/bad.rb'


# == CODE =================================================================== #

class Scanner

    attr_accessor :forms,
                  :question_sets,
                  :questions

    def initialize(filename, sheetname)

        # logger name
        @logger                     = Log.new(Code.for_class(self))

        # xls
        Spreadsheet.client_encoding = 'UTF-8'
        @book                       = Spreadsheet.open(filename, 'r')
        @main_sheet                 = @book.worksheet(sheetname)

        # data
        @rows                       = []
        @forms                      = []
        @question_sets              = []
        @questions                  = []

        @logger.info 'READING ' + filename + '::' + sheetname

        @logger.info 'Fetching rows...'
        fetch_rows

        @logger.info 'Parsing forms, questions sets and questions...'
        parse_forms_question_sets_and_questions

        @logger.info 'Validating questions...'
        validate_questions

        @logger.info 'Validating question sets...'
        validate_question_sets

        @logger.info 'Parsing bads...'
        read_bads

        self
    end

    def validate_question_sets
        for qs in @question_sets
            begin
                qs.validate
            rescue Exception => e
                @logger.error e.message
            end
        end
    end

    def validate_questions
        for q in @questions
            begin
                q.validate
            rescue Exception => e
                @logger.error e.message
            end
        end
    end

    def fetch_rows
        @rows = []
        @main_sheet.each do |row|
            if row.length == 0
                break
            end
            @rows << row
        end
    end

    def parse_forms_question_sets_and_questions

        for row in @rows

            # ignore rows with strikeout
            f = row.format(0)
            next if f.font.strikeout

            # form / question_sets / questions
            ref         = row[0]
            form        = row[3].to_s
            parent      = row[1]
            i_or_iset   = row[2]
            driver      = row[4]
            accronym    = row[7]
            name        = row[11]
            desc        = row[12]
            loop_desc   = row[15]
            type        = row[13]
            options     = row[14]
            defaultUnit = row[20]
            repeatable  = loop_desc!=nil

            # Form
            if form != nil && form.length > 0 && form.upcase != 'AGGREGATION' && form.upcase != 'METHOD'
                m = /^(.+):\s*(.*)$/.match(form.to_s)
                if m != nil
                    current_form        = Form.new
                    current_form.number = /([0-9]+)/.match(m[1])[1].to_i
                    current_form.name   = m[2].strip
                    current_form.code   = m[1].strip
                    @forms << current_form
                    @logger.debug "FORM '" + current_form.number.to_s + "' code='" + current_form.code + "' name='" + current_form.name + "'"
                end
            end

            # Question set
            if ref != nil and i_or_iset == 0
                qs            = QuestionSet.new
                qs.ref        = ref
                qs.accronym   = accronym
                qs.repeatable = repeatable

                qs.text = name
                if parent != nil
                    qs.parent = question_sets.select { |q| q.ref == parent }.first
                end
                qs.form = current_form

                question_sets << qs
                @logger.debug "QUESTION_SET '" + qs.accronym + "' (" + (qs.parent.accronym rescue "") + ") : '" + qs.text + "'"
            end

            # Question
            if ref != nil and i_or_iset == 1
                q          = Question.new
                q.ref      = ref
                q.accronym = accronym
                if parent != nil
                    q.question_set = question_sets.select { |qs| qs.ref == parent }.first
                end
                q.text = name
                q.type = Code.make(type)
                if q.type == "MULTIPLE"
                    q.options = options
                end
                if driver != nil
                    q.driver = driver
                end
                if defaultUnit != nil
                    q.unit_default = defaultUnit
                end
                questions << q
                @logger.debug "QUESTION '" + q.accronym + "' (" + (q.question_set.accronym rescue "") + ") : '" + q.text + "'"

            end


        end

        @logger.info "FORMS found: " + forms.length.to_s
        @logger.info "QUESTION_SETS found: " + question_sets.length.to_s
        @logger.info "QUESTIONS found: " + questions.length.to_s


    end


    def read_bads
        for row in @rows

            ref                  = row[0]
            # BADs
            bad_key              = row[1]
            bad_name             = row[2]
            bad_method           = row[3]
            bad_rank             = row[4]
            bad_specific_purpose = row[5]
            bad_ac_key           = row[6]
            bad_ac               = row[7]
            bad_asc_key          = row[8]
            bad_asc              = row[9]
            bad_at_key           = row[10]
            bad_at               = row[11]
            bad_as_key           = row[12]
            bad_as               = row[13]
            bad_ao               = row[14]
            bad_a_unit           = row[15]
            bad_value            = row[16]


            if ref == nil && bad_key != nil

                # build bad
                bad = Bad.new(bad_key,
                              bad_name,
                              bad_method,
                              bad_rank,
                              bad_specific_purpose,
                              bad_ac_key,
                              bad_ac,
                              bad_asc_key,
                              bad_asc,
                              bad_at_key,
                              bad_at,
                              bad_as_key,
                              bad_as,
                              bad_ao,
                              bad_a_unit,
                              bad_value)

                bad.name = bad_key


                @logger.debug "BAD '" + bad.key + "'"
            end

        end
    end

end