require 'rubygems'
require 'spreadsheet'
require 'i18n'

Spreadsheet.client_encoding = 'UTF-8'

class Tab
  attr_accessor :number, :name, :code
end

class QuestionSet
  attr_accessor :ref, :accronym, :text, :repeatable, :parent, :tab
end

class Question
  attr_accessor :ref, :accronym, :text, :type, :options, :question_set, :tab, :driver
end

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

tabs = []
question_sets = []
questions = []
options_lists = []

current_tab = nil


book = Spreadsheet.open('awac_data_25-07-2014/AWAC-entreprise-calcul_FE.xls', 'r')

sheet = book.worksheet('site entreprise-activityData') # can use an index or worksheet name

nil_count = 0

sheet.each do |row|

  if row.length == 0
    nil_count = nil_count + 1
    if nil_count > 10
      break
    end
  else
    nil_count = 0
  end


  f = row.format(0)
  next if f.font.strikeout

  ref = row[0]
  tab = row[3].to_s
  parent = row[1]
  i_or_iset= row[2]
  driver = row[4]
  accronym = row[7]
  name = row[11]
  desc = row[12]
  loop_desc = row[15]
  type = row[13]
  options = row[14]
  repeatable = loop_desc!=nil


  if current_tab != nil and current_tab.number != '1'
    if parent == 1
      parent = nil
    end
  end

  # handle tabs
  if tab != nil
    if tab.start_with? "à remettre dans le TAB"

      current_tab = tabs.select { |t| t.number == tab.gsub("à remettre dans le TAB", "").strip }.first

    else
      if tab.to_s.start_with? 'TAB'

        m = /^TAB([0-9]+):\s*(.*)$/.match(tab.to_s)

        current_tab = Tab.new
        current_tab.number = m[1]
        current_tab.name = m[2]
        current_tab.code = make_code(m[2])

        tabs.push(current_tab)

        puts "TAB" + current_tab.number.to_s + "\t" + current_tab.name+ "\t" + current_tab.name+ "\t" + current_tab.name

        # puts "Tab created: " + current_tab.code
      end
    end
  end


  if ref != nil
    puts accronym + "_TITLE" + "\t" + name+ "\t" + name+ "\t" + name
    if desc != nil
      puts accronym + "_DESC" + "\t" + desc+ "\t" + desc+ "\t" + desc
    end
    if loop_desc != nil
      puts accronym + "_LOOPDESC" + "\t" + loop_desc+ "\t" + loop_desc+ "\t" + loop_desc
    end

    if i_or_iset == 0
      qs = QuestionSet.new
      qs.ref = ref
      qs.accronym = accronym
      qs.repeatable = repeatable

      qs.text = name
      if parent != nil
        qs.parent = question_sets.select { |q| q.ref == parent }.first
      end
      qs.tab = current_tab

      question_sets.push(qs)


      # puts "  QuestionSet created: " + qs.accronym + "(" + (qs.parent.accronym rescue "") + ")"
    end

    if i_or_iset == 1
      q = Question.new
      q.ref = ref
      q.accronym = accronym
      if parent != nil
        q.question_set = question_sets.select { |qs| qs.ref == parent }.first
      end
      q.text = name
      q.tab = current_tab
      q.type= make_code(type)
      if q.type == "MULTIPLE"
        q.options = options
      end
      if driver != nil
        q.driver = driver
      end


      questions.push(q)

      # puts "  Question    created: " + q.accronym + "(" + (q.question_set.accronym rescue "") + ") : " + q.type + (' ' + q.options.options.to_s rescue '')


    end

  end
end

def make_tab(t)
  txt = '
    Form tab__N__Form = new Form("TAB__N__");
    session.saveOrUpdate(tab__N__Form);
  '
  txt = txt.gsub /__N__/, t.number.to_s
  header = '        // == TAB' + t.number.to_s + ' ' + ('=' * (80 - t.number.to_s.length - 7))
  header + "\n" + txt + "\n" + "\n"
end

def make_question_set(qs)

  txt = '
    QuestionSet __VARNAME__ = new QuestionSet(QuestionCode.__ACCRONYM__, __REPEATABLE__, __PARENT__);
    session.saveOrUpdate(__VARNAME__);
  '
  txt2 = '
    __TABNAME__.getQuestionSets().add(__VARNAME__);
    session.saveOrUpdate(__TABNAME__);
  '
  txt = txt.gsub /__VARNAME__/, qs.accronym.downcase
  txt = txt.gsub /__ACCRONYM__/, qs.accronym.upcase
  txt = txt.gsub /__REPEATABLE__/, (qs.repeatable ? 'true' : 'false')
  txt = txt.gsub /__PARENT__/, ((qs.parent != nil) ? qs.parent.accronym.downcase : 'null')

  txt2 = txt2.gsub /__VARNAME__/, qs.accronym.downcase
  txt2 = txt2.gsub /__TABNAME__/, ('tab'+ qs.tab.number.to_s + 'Form')

  header = qs.accronym + ' (' + qs.text + ')'
  qss = qs.parent
  while qss != nil
    header = qss.accronym + '(' + qss.text + ')' + ' > ' + header
    qss = qss.parent
  end

  header = qs.text + "\n        // " + header
  header = '== ' + qs.accronym + ' ' + ('=' * (80 - qs.accronym.length - 4)) + "\n        // " + header

  if qs.parent != nil
    txt2 = ''
  end

  '        // ' + header + "\n" + txt + "\n" + txt2 + "\n"


end


def make_question(q)

  driver = q.driver.to_s

  if driver == nil or driver.to_s.strip.length == 0
    driver = 'null'
  else
    driver = driver.to_s.strip
  end

  found = false
  txt = nil

  if q.type == 'NUMBER'
    found= true

    txt = 'session.saveOrUpdate(new IntegerQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, null, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
    txt = txt.gsub /__DEFAULT__/, driver
  end

  if q.type == 'MULTIPLE'
    found= true
    txt = 'session.saveOrUpdate(new ValueSelectionQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, CodeList.__LIST__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase

    if q.options != nil and q.options.start_with? 'Liste:'
      txt = txt.gsub /__LIST__/, make_code(q.options.gsub('Liste:', '').strip)
    else
      if q.options != nil and q.options.start_with? 'ListeCompound:'
        txt = txt.gsub /__LIST__/, make_code(q.options.gsub('ListeCompound:', '').strip)
      else
        txt = txt.gsub /__LIST__/, q.accronym.upcase + '_OPTIONS'
      end
    end
  end

  if q.type == 'YES_NO'
    found= true

    txt = 'session.saveOrUpdate(new BooleanQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase

    if driver == true or
        driver.upcase == 'YES' or
        driver.upcase == 'Y' or
        driver.upcase == 'OUI' or
        driver.upcase == 'O' or
        driver.upcase == 'TRUE' or
        driver.upcase == 'T' or
        driver.upcase == '1'
      txt = txt.gsub /__DEFAULT__/, 'true'
    else
      if driver == false or
          driver.upcase == 'NO' or
          driver.upcase == 'N' or
          driver.upcase == 'NON' or
          driver.upcase == 'N' or
          driver.upcase == 'FALSE' or
          driver.upcase == 'F' or
          driver.upcase == '0'
        txt = txt.gsub /__DEFAULT__/, 'false'
      else
        txt = txt.gsub /__DEFAULT__/, 'null'
      end
    end

  end

  if q.type == 'UNIT_AREA'
    found= true

    txt = 'session.saveOrUpdate(new DoubleQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, surfaceUnits, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
    txt = txt.gsub /__DEFAULT__/, driver
  end

  if q.type == 'DOCUMENT'
    found= true

    txt = 'session.saveOrUpdate(new StringQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
  end

  if q.type == 'UNIT_ENERGY'
    found= true
    txt = 'session.saveOrUpdate(new DoubleQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, energyUnits, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
    txt = txt.gsub /__DEFAULT__/, driver
  end

  if q.type == 'UNIT_MASS' or q.type == 'UNIT_MASS_KG'
    found= true
    txt = 'session.saveOrUpdate(new DoubleQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, massUnits, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
    txt = txt.gsub /__DEFAULT__/, driver
  end

  if q.type == 'UNIT_POWER'
    found= true
    txt = 'session.saveOrUpdate(new DoubleQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, powerUnits, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
    txt = txt.gsub /__DEFAULT__/, driver
  end

  if q.type == 'UNIT_TIME'
    found= true
    txt = 'session.saveOrUpdate(new DoubleQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, timeUnits, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
    txt = txt.gsub /__DEFAULT__/, driver
  end

  if q.type == 'UNIT_LENGTH'
    found= true
    txt = 'session.saveOrUpdate(new DoubleQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, lengthUnits, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
    txt = txt.gsub /__DEFAULT__/, driver
  end

  if q.type == 'UNIT_VOLUME'
    found= true
    txt = 'session.saveOrUpdate(new DoubleQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, volumeUnits, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
    txt = txt.gsub /__DEFAULT__/, driver
  end

  if q.type == 'UNIT_MONEY'
    found= true
    txt = 'session.saveOrUpdate(new DoubleQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, moneyUnits, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
    txt = txt.gsub /__DEFAULT__/, driver
  end

  if q.type == 'TEXT'
    found= true

    txt = 'session.saveOrUpdate(new StringQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
    txt = txt.gsub /__DEFAULT__/, driver
  end

  if q.type == 'PCT'
    found= true

    txt = 'session.saveOrUpdate(new DoubleQuestion(__PARENT__, 0, QuestionCode.__ACCRONYM__, null, __DEFAULT__));'
    txt = txt.gsub /__PARENT__/, q.question_set.accronym.downcase
    txt = txt.gsub /__ACCRONYM__/, q.accronym.upcase
    txt = txt.gsub /__DEFAULT__/, driver.inspect
  end

  if found

    header = q.accronym + ' (' + q.text + ')'
    qs = q.question_set
    while qs != nil
      header = qs.accronym + '(' + qs.text + ')' + ' > ' + header
      qs = qs.parent
    end

    header = q.text + "\n        // " + header
    header = '== ' + q.accronym + ' ' + ('=' * (80 - q.accronym.length - 4)) + "\n        // " + header


    '        // ' + header + "\n" + txt + "\n" + "\n"
  else
    'ERROR: ' + q.type
  end


end


# Create the options lists

puts "BEGIN_CODE"
for t in tabs

  puts make_tab(t)

end

for qs in question_sets

  puts make_question_set(qs)

end


for q in questions

  puts make_question(q)

end

puts "END_CODE"


b = Spreadsheet::Workbook.new


def create_ws(b, ln, good_sheet)

  # excel

  s = b.create_worksheet
  s.name = ln

  bold = Spreadsheet::Format.new :weight => :bold

  if good_sheet[0, 0] == 'ActivitySource_KEY' or good_sheet[0, 0] == 'ActivityType_KEY'
    s[0, 0] = good_sheet[0, 0]

    s.row(0).set_format(0, bold)

    for i in 1..good_sheet.rows.length
      s[i, 0] = good_sheet[i, 0]
    end
  else
    s[0, 0] = 'KEY'
    s[0, 1] = 'LABEL_EN'
    s[0, 2] = 'LABEL_FR'
    s[0, 3] = 'LABEL_NL'

    s.row(0).set_format(0, bold)
    s.row(0).set_format(1, bold)
    s.row(0).set_format(2, bold)
    s.row(0).set_format(3, bold)


    for i in 1..good_sheet.rows.length
      s[i, 0] = good_sheet[i, 0]
      s[i, 1] = good_sheet[i, 1]
      s[i, 2] = good_sheet[i, 1]
      s[i, 3] = good_sheet[i, 1]
    end
  end
end


def create_code_class(ln, good_sheet)

  # Code file
  lines = []

  header = '
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "__LLN__"))})
public class __LN__Code extends Code {

    private static final long serialVersionUID = 1L;

    protected __LN__Code() {
        super(CodeList.__LN__);
    }

    public __LN__Code(String key) {
        this();
        this.key = key;
    }
'.gsub('__LN__', ln).gsub('__LLN__', ln.downcase)

  for i in 1..good_sheet.rows.length
    r = good_sheet.row(i)
    next if r[0] == nil
    l = 'public static final ' + ln + 'Code ' + r[0] + ' = new ' + ln + 'Code("' + r[0] + '");'
    lines.push l
  end

  footer = '}'

  File.open('/tmp/codes/' + ln + 'Code.java', 'w') { |file| file.write(header + lines.join("\n") + footer) }


end


hardlists = [
    'ActivityCategory',
    'ActivitySubCategory',
    'ActivityType',
    'ActivitySource',
    'IndicatorCategory'
]

for l in hardlists
  good_sheet = book.worksheets.select { |s| s.name == l }.first
  if good_sheet
    create_ws(b, l, good_sheet)
    create_code_class(l, good_sheet)
  else
    puts 'ERROR for sheet ' + ln
  end
end


for q in questions
  if q.options != nil
    ln = nil

    if q.options != nil and q.options.start_with? 'Liste:'
      ln = make_code(q.options.gsub('Liste:', '').strip)
    else
      if q.options != nil and q.options.start_with? 'ListeCompound:'
        ln = make_code(q.options.gsub('ListeCompound:', '').strip)
      end
    end

    if ln != nil and b.worksheets.select { |s| make_code(s.name) == ln }.length == 0

      good_sheet = book.worksheets.select { |s| make_code(s.name) == ln }.first

      if good_sheet
        create_ws(b, ln, good_sheet)
      else
        puts 'ERROR for sheet ' + ln
      end
    end
  end
end

b.write '/tmp/codes/codes_to_import_full.xls'
