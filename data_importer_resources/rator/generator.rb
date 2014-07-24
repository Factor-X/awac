require './form'
require './question_set'
require './question'

class Generator

  attr_accessible :forms,
                  :question_sets,
                  :questions,

                  :generated_code_lists

  def new
    @forms = []
    @question_sets = []
    @questions = []

    @generated_code_lists = [
        'ActivityCategory',
        'ActivitySubCategory',
        'ActivityType',
        'ActivitySource',
        'IndicatorCategory'
    ]
  end

  def run
    read_survey_from_xls('../awac_data_17-07-2014/AWAC-entreprise-calcul_FE.xls')
    write_survey_translations_to_xls('../translations/survey.xls')
    write_survey_code_to_file('/tmp/codes/survey.java')
    write_code_files_in_directory('/tmp/codes')
  end

  def read_survey_from_xls(path)
    # TODO
  end

  def write_survey_code_to_file(path)
    # TODO
  end

  def write_survey_translations_to_xls(path)
    # TODO
  end

  def write_code_files_in_directory(path)
    # TODO
  end

end