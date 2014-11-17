# encoding: UTF-8

# == DEPENDENCIES =========================================================== #

require 'rubygems'
require 'i18n'

# == CODE =================================================================== #


class Code
    def self.make(str)
        I18n.enforce_available_locales = false

        name = str
        name = I18n.transliterate(name)
        name = name.upcase
        name = name.gsub /%/, ' PCT '
        name = name.gsub /[^a-zA-Z0-9_]/, '_'
        name = name.gsub /_+/, '_'
        name = name.gsub /_$/, ''
        name = name.gsub /^_/, ''
        if /^[0-9]/.match(name) != nil
            name = '_' + name
        end
        name
    end

    def self.for_class(i)
        i.class.to_s + '#' + i.__id__.to_s
    end

    def self.for_static_class(i)
        i.to_s
    end
end