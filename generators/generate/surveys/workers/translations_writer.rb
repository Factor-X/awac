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
        @target_filename = "#{ROOT}/data_importer_resources/translations/translations_#{name}.xls"
        @forms           = forms
        @question_sets   = question_sets
        @questions       = questions
    end

    def execute
        @logger.info("READING #{@target_filename}...")
        @target_book = Spreadsheet::Workbook.new

        @main_sheet = @target_book.create_worksheet :name => 'SURVEY'


        @main_sheet[0, 0] = 'KEY'
        @main_sheet[0, 1] = 'LABEL_EN'
        @main_sheet[0, 2] = 'LABEL_FR'
        @main_sheet[0, 3] = 'LABEL_NL'

        bold = Spreadsheet::Format.new :weight => :bold
        @main_sheet.row(0).set_format(0, bold)
        @main_sheet.row(0).set_format(1, bold)
        @main_sheet.row(0).set_format(2, bold)
        @main_sheet.row(0).set_format(3, bold)

        current_line = 1

        @logger.info("Inserting FORMS ...")
        for form in @forms

            @main_sheet[current_line, 0] = form.code
            @main_sheet[current_line, 1] = form.name
            @main_sheet[current_line, 2] = form.name
            @main_sheet[current_line, 3] = form.name
            current_line                 = current_line + 1

        end

        @logger.info("Inserting QUESTIONS_SETS ...")
        for qs in @question_sets

            @main_sheet[current_line, 0] = qs.accronym + '_TITLE'
            @main_sheet[current_line, 1] = qs.text
            @main_sheet[current_line, 2] = qs.text
            @main_sheet[current_line, 3] = qs.text
            current_line                 = current_line + 1

            if qs.loop_descriptor != nil
                @main_sheet[current_line, 0] = qs.accronym + '_LOOPDESC'
                @main_sheet[current_line, 1] = qs.loop_descriptor
                @main_sheet[current_line, 2] = qs.loop_descriptor
                @main_sheet[current_line, 3] = qs.loop_descriptor
                current_line                 = current_line + 1
            end

        end

        @logger.info("Inserting QUESTIONS ...")
        for q in @questions

            @main_sheet[current_line, 0] = q.accronym + '_TITLE'
            @main_sheet[current_line, 1] = q.text
            @main_sheet[current_line, 2] = q.text
            @main_sheet[current_line, 3] = q.text
            current_line                 = current_line + 1

            if q.description != nil
                @main_sheet[current_line, 0] = q.accronym + '_DESC'
                @main_sheet[current_line, 1] = q.description
                @main_sheet[current_line, 2] = q.description
                @main_sheet[current_line, 3] = q.description
                current_line                 = current_line + 1
            end
        end

        @logger.info('WRITING ' + @target_filename)
        @target_book.write @target_filename
    end


end