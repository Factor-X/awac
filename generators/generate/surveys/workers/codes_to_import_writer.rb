# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

require 'rubygems'
require 'spreadsheet'

require_relative '../util/code.rb'
require_relative '../util/log.rb'

# == CODE =================================================================== #

class CodesToImportWriter

    def initialize(name, filename, questions)
        @logger                 = Log.new(Code.for_class(self))
        @filename               = filename
        @temp_filename          = "/tmp/codes_to_import_full_#{Time.now.strftime('%Y%m%dT%H%M%S')}.xls"
        @target_filename        = "#{ROOT}/data_importer_resources/codes/codes_to_import_#{name}.xls"
        @target_common_filename = "#{ROOT}/data_importer_resources/codes/codes_to_import_common.xls"
        @questions              = questions
    end


    def execute
        @logger.info("READING #{@filename}...")
        @source_book = Spreadsheet.open @filename

        write_common_lists
        write_actual_lists
    end

    def write_common_lists
        @target_book = Spreadsheet::Workbook.new
        common_lists = [
            'ActivityCategory',
            'ActivitySubCategory',
            'ActivityType',
            'ActivitySource',
            'IndicatorCategory'
        ]

        for l in common_lists
            source_sheet = get_source_sheet_by_name(l)
            create_target_worksheet_from source_sheet
        end

        @logger.info('WRITING ' + @target_common_filename)
        @target_book.write @target_common_filename
    end

    def write_actual_lists
        @target_book = Spreadsheet::Workbook.new

        validate_options_of_all_questions
        for q in @questions
            if q.options != nil
                source_sheet = get_source_sheet_for_question(q)
                create_target_worksheet_from source_sheet
            end
        end

        @logger.info('WRITING ' + @target_filename)
        @target_book.write @target_filename
    end


    def get_source_sheet_by_name(l)
        @source_book.worksheets.select { |s| s.name == l }.first
    end

    def validate_options_of_all_questions
        for q in @questions
            if q.options != nil
                get_source_sheet_for_question q
            end
        end
    end

    def get_source_sheet_for_question(q)
        if q.options.include? ':'
            parts = q.options.split(':').collect { |p| p.strip }
            if Code.make(parts[0]) == 'LISTE' or Code.make(parts[0]) == 'LISTECOMPOUND'
                ln = Code.make(parts[1])
            else
                raise Exception.new "Question #{q.accronym} has not recognized options in #{@filename}: #{q.options}"
            end
        else
            @logger.warn "Question #{q.accronym} does not follow the conventions for its options in #{@filename}: #{q.options}. Assuming it is the list name..."
            ln = Code.make(q.options)
        end

        if ln != nil
            sheet = @source_book.worksheets.select { |s| Code.make(s.name) == ln }.first
            if sheet == nil
                raise Exception.new "Sheet #{ln} not found in #{@filename}"
            end
            return sheet
        end
        return nil
    end

    def create_target_worksheet_from(source_sheet)

        name = Code.make(source_sheet.name)

        @logger.info "Creating worksheet #{name}..."
        target_sheet = @target_book.create_worksheet :name => name


        bold = Spreadsheet::Format.new :weight => :bold

        if source_sheet[0, 0] == 'ActivitySource_KEY' or source_sheet[0, 0] == 'ActivityType_KEY' or source_sheet[0, 0] == 'ActivitySubCategory_KEY'
            target_sheet[0, 0] = source_sheet[0, 0]
            target_sheet.row(0).set_format(0, bold)
            for i in 1..source_sheet.rows.length
                target_sheet[i, 0] = source_sheet[i, 0]
            end
        else
            target_sheet[0, 0] = 'KEY'
            target_sheet[0, 1] = 'LABEL_EN'
            target_sheet[0, 2] = 'LABEL_FR'
            target_sheet[0, 3] = 'LABEL_NL'

            target_sheet.row(0).set_format(0, bold)
            target_sheet.row(0).set_format(1, bold)
            target_sheet.row(0).set_format(2, bold)
            target_sheet.row(0).set_format(3, bold)

            for i in 1..source_sheet.rows.length
                target_sheet[i, 0] = source_sheet[i, 0]
                target_sheet[i, 1] = source_sheet[i, 1]
                target_sheet[i, 2] = source_sheet[i, 1]
                target_sheet[i, 3] = source_sheet[i, 1]
            end

            extra_column = 4

            for i in (1..source_sheet.row(0).length)
                # Look for a foreign key column
                if source_sheet[0, i].to_s.end_with? '_KEY'
                    for j in 0..source_sheet.rows.length
                        target_sheet[j, extra_column] = source_sheet[j, i]
                    end
                    target_sheet.row(0).set_format(extra_column, bold)
                    extra_column = extra_column + 1
                end
            end


        end

        @logger.info "Worksheet #{name} has been created."
    end

end