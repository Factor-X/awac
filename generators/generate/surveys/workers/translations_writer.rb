# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

require 'rubygems'
require 'spreadsheet'

require_relative '../util/code.rb'
require_relative '../util/log.rb'

# == CODE =================================================================== #

class TranslationsWriter

    def initialize(name, forms, question_sets, questions)
        @logger          = Log.new(Code.for_class(self))
        @target_filename = "#{ROOT}/data_importer_resources/translations/translations_#{name}.generated.xls"
        @forms           = forms
        @question_sets   = question_sets
        @questions       = questions
    end

    def execute
        @logger.info("READING #{@target_filename}...")

        @target_book = Spreadsheet::Workbook.new
        bold         = Spreadsheet::Format.new :weight => :bold

        @forms_sheet       = @target_book.create_worksheet :name => 'FORMS'
        @forms_sheet[0, 0] = 'KEY'
        @forms_sheet[0, 1] = 'LABEL_EN'
        @forms_sheet[0, 2] = 'LABEL_FR'
        @forms_sheet[0, 3] = 'LABEL_NL'
        @forms_sheet.row(0).set_format(0, bold)
        @forms_sheet.row(0).set_format(1, bold)
        @forms_sheet.row(0).set_format(2, bold)
        @forms_sheet.row(0).set_format(3, bold)

        @question_sets_sheet       = @target_book.create_worksheet :name => 'QUESTION_SETS'
        @question_sets_sheet[0, 0] = 'KEY'
        @question_sets_sheet[0, 1] = 'LABEL_EN'
        @question_sets_sheet[0, 2] = 'LABEL_FR'
        @question_sets_sheet[0, 3] = 'LABEL_NL'
        @question_sets_sheet.row(0).set_format(0, bold)
        @question_sets_sheet.row(0).set_format(1, bold)
        @question_sets_sheet.row(0).set_format(2, bold)
        @question_sets_sheet.row(0).set_format(3, bold)

        @questions_sheet       = @target_book.create_worksheet :name => 'QUESTIONS'
        @questions_sheet[0, 0] = 'KEY'
        @questions_sheet[0, 1] = 'LABEL_EN'
        @questions_sheet[0, 2] = 'LABEL_FR'
        @questions_sheet[0, 3] = 'LABEL_NL'
        @questions_sheet.row(0).set_format(0, bold)
        @questions_sheet.row(0).set_format(1, bold)
        @questions_sheet.row(0).set_format(2, bold)
        @questions_sheet.row(0).set_format(3, bold)

        current_line = 1
        @logger.info('Inserting FORMS ...')
        for form in @forms

            @forms_sheet[current_line, 0] = form.code
            @forms_sheet[current_line, 1] = form.name
            @forms_sheet[current_line, 2] = form.name
            @forms_sheet[current_line, 3] = form.name
            current_line                  = current_line + 1

        end

        current_line = 1
        @logger.info('Inserting QUESTIONS_SETS ...')
        for qs in @question_sets

            @question_sets_sheet[current_line, 0] = qs.accronym
            @question_sets_sheet[current_line, 1] = qs.text
            @question_sets_sheet[current_line, 2] = qs.text
            @question_sets_sheet[current_line, 3] = qs.text
            current_line                          = current_line + 1

            if qs.loop_descriptor != nil
                @question_sets_sheet[current_line, 0] = qs.accronym + '_LOOPDESC'
                @question_sets_sheet[current_line, 1] = qs.loop_descriptor
                @question_sets_sheet[current_line, 2] = qs.loop_descriptor
                @question_sets_sheet[current_line, 3] = qs.loop_descriptor
                current_line                          = current_line + 1
            end

        end

        current_line = 1
        @logger.info('Inserting QUESTIONS ...')
        for q in @questions

            @questions_sheet[current_line, 0] = q.accronym
            @questions_sheet[current_line, 1] = q.text
            @questions_sheet[current_line, 2] = q.text
            @questions_sheet[current_line, 3] = q.text
            current_line                      = current_line + 1

            if q.description != nil
                @questions_sheet[current_line, 0] = q.accronym + '_DESC'
                @questions_sheet[current_line, 1] = q.description
                @questions_sheet[current_line, 2] = q.description
                @questions_sheet[current_line, 3] = q.description
                current_line                      = current_line + 1
            end
        end

        @logger.info('WRITING ' + @target_filename)
        @target_book.write @target_filename
    end


end