def make_code(str)
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