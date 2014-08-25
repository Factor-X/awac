# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

require 'rubygems'
require 'logger'
require 'term/ansicolor'
include Term::ANSIColor

# == CODE =================================================================== #


class Logger
    def self.custom_level(tag)
        SEV_LABEL << tag
        idx = SEV_LABEL.size - 1

        define_method(tag.downcase.gsub(/\W+/, '_').to_sym) do |progname, &block|
            add(idx, nil, progname, &block)
        end
    end

    # now add levels
    custom_level 'SECTION'
    custom_level 'SUB_SECTION'
end

class Log

    @@default_level = 'DEBUG'

    def self.default_level= (v)
        @@default_level = v
    end

    def initialize(name, log_level = nil)

        @progname = name
        @instance = Logger.new(STDOUT)

        if log_level == nil
            log_level = @@default_level
        end

        @instance.level = Logger::INFO if log_level == 'INFO'
        @instance.level = Logger::WARN if log_level == 'WARN'
        @instance.level = Logger::DEBUG if log_level == 'DEBUG'
        @instance.level = Logger::ERROR if log_level == 'ERROR'
        @instance.level = Logger::FATAL if log_level == 'FATAL'

        @instance.formatter = proc { |severity, datetime, progname, msg|
            res = ""

            if severity == 'SUB_SECTION'
                res += "\n"
            end

            if severity == 'SECTION'
                res += "\n"
                res += ' ' * 44 + ' ' + ('=' * 80)
                res += "\n"
            end

            res += bold
            res += add_color(severity)
            res += '[%12s]' % [severity]
            res += reset
            res += ' '

            res += yellow
            res += '%s' % [datetime.strftime('%H:%M:%S')] # %Y-%m-%d
            res += reset
            res += ' '

            res += magenta
            res += '%20s' % [progname]
            res += reset
            res += ' '

            res += add_color(severity)

            res += '%s' % [msg]
            res += reset
            res += ' '

            res += "\n"

            if severity == 'SECTION'
                res += ' ' * 44 + ' ' + ('=' * 80)
                res += "\n"
            end

            res
        }

    end

    def add_color(severity)
        if severity == 'INFO'
            return green
        end
        if severity == 'WARN'
            return yellow
        end
        if severity == 'DEBUG'
            return cyan
        end
        if severity == 'ERROR'
            return red
        end
        if severity == 'FATAL'
            return on_red + white
        end
        if severity == 'SECTION'
            return bold + white
        end
        if severity == 'SUB_SECTION'
            return bold + white
        end
    end

    def debug(data)
        @instance.debug(@progname) { data }
    end

    def info(data)
        @instance.info(@progname) { data }
    end

    def warn(data)
        @instance.warn(@progname) { data }
    end

    def error(data)
        @instance.error(@progname) { data }
    end

    def fatal(data)
        @instance.fatal(@progname) { data }
    end

    def section(data)
        @instance.section(@progname) { data }
    end

    def sub_section(data)
        @instance.sub_section(@progname) { '== ' + data +' '+ ('=' * (80 - 4 - data.length)) }
    end

end
