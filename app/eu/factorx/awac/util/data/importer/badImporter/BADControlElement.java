package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.BooleanQuestion;
import eu.factorx.awac.models.data.question.type.NumericQuestion;
import eu.factorx.awac.models.data.question.type.StringQuestion;
import eu.factorx.awac.models.data.question.type.ValueSelectionQuestion;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.CodeConversionService;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.QuestionService;
import eu.factorx.awac.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by florian on 4/09/14.
 */
@Component
public class BADControlElement {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private CodeConversionService codeConversionService;

    @Autowired
    private CodeLabelService codeLabelService;


    /*
     * group 1 : the question code
     * group 2 (optional) : the conversion criterion
     */
    private Pattern questionPattern = Pattern.compile("(A[A-Z]*[0-9]+)(\\[([A-Z_]+)\\])?");


    BADLog.LogLine logLine;

    public void setBadLog(BADLog.LogLine logLine) {
        this.logLine = logLine;
    }

    /**
     * cannot be null
     * control BADKey
     * => look into BaseActivityDataCode to found the code
     * return the content
     */
    public void controlBADKey(String cellContent, BAD bad) {

        String badKey = cellContent;
        if (badKey == null || badKey.length() == 0) {
            logLine.addError("There is no badKey : " + cellContent);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        if (!controlList(BaseActivityDataCode.class, badKey)) {
            logLine.addError("This is not a badKey : " + cellContent);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        //complete the bad
        bad.setBaseActivityDataCode(badKey.toUpperCase());
    }

    /**
     * control the name of the BAD
     * The name is only a string
     * cannot be null
     */
    public void controlName(String content, BAD bad) {

        if (content == null || content.length() == 0) {
            logLine.addError("There is no name: " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        //complete the bad
        bad.setName(content);
    }

    /**
     * Rank cannot be null and can be a number
     * return null otherwise
     */
    public void controlRank(String content, BAD bad) {

        if (content == null || content.length() == 0) {
            logLine.addWarn("There rank is null");

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }
        try {
            bad.setRank(new Double(Double.parseDouble(content)).intValue());
        } catch (NumberFormatException e) {
            logLine.addError("The rank is not null but it's not a valid number  : " + content);
        }

        //null => cannot be generated
        bad.setCanBeGenerated(false);
        return;
    }

    /**
     * Can be an answer or a string or can be null
     */
    public void controlSpecificPurpose(String content, BAD bad) {
        if (content != null && content.length() > 0) {

            //test if the specific purpose is a code
            //control if the question is a text
            if (controlList(QuestionCode.class, content)) {
                if (controlQuestionType(content, StringQuestion.class)) {
                    logLine.addDebug("There is a SpecificPurpose and it's a Stringquestion");

                    bad.setSpecificPurpose("toString(question" + content + "Answer)");
                    bad.addQuestion(content);

                } else {
                    logLine.addError("SpecificPurpose : it's a question but not a StringQuestion : SpecificPurpose will be null");
                }
            } else {
                logLine.addInfo("SpecificPurpose is a string");
                bad.setSpecificPurpose("\"" + content + "\"");
            }
        } else {
            logLine.addInfo("The SpecificPurpose is null");
        }
    }

    /**
     * Cannot be nul, always a activityCategory
     */
    public void controlActivityCategory(String content, BAD bad) {

        if (content == null || content.length() == 0) {
            logLine.addError("There is no activityCategory : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        //save all data into one object
        if (!controlList(ActivityCategoryCode.class, content)) {
            logLine.addError("This activityCategory was not found : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }
        bad.setActivityCategoryCode("ActivityCategoryCode." + content);
    }

    /**
     * Cannot be nul, can be  a activitySubCategory or a question using a list composed to  activitySubCategory element
     */
    public void controlActivitySubCategory(String content, BAD bad) {

        if (content == null || content.length() == 0) {
            logLine.addError("There is no activitySubCategory : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }
        //test if the activitySubCategory is a code
        if (controlList(ActivitySubCategoryCode.class, content)) {
            logLine.addDebug("There is a activitySubCategory and it's an ActivitySubCategoryCode");

            //add to bad
            bad.setActivitySubCategory("ActivitySubCategoryCode." + content);

        } else {

            Matcher matcher = questionPattern.matcher(content);

            if (matcher.find()) {


                if (controlList(QuestionCode.class, matcher.group(1))) {

                    //load the question
                    Question question = questionService.findByCode(new QuestionCode(matcher.group(1)));

                    if (question instanceof ValueSelectionQuestion) {
                        if (!codeConversionService.isSublistOf(((ValueSelectionQuestion) question).getCodeList(), CodeList.ActivitySubCategory)) {
                            logLine.addError("ActivitySubCategory is a ValueSelectionQuestion but this list is not a (sub)list of ActivitySubCategory");
                        }
                    } else {
                        logLine.addError("ActivitySubCategory is a question but not a ValueSelectionQuestion");
                    }

                    String value = "toActivitySubCategoryCode(question" + matcher.group(1) + "Answer)";

                    //test the criteron conversion
                    if (matcher.group(2) != null) {
                        value = "convertCode(" + value + ", " + matcher.group(2) + "," + ActivitySubCategoryCode.class.getName() + ")";
                    }

                    //add to bad
                    bad.setActivitySubCategory(value);
                    bad.addQuestion(matcher.group(1));
                    return;
                }

            }
            logLine.addError("There is a activitySubCategory but it's not an ActivitySubCategoryCode or an answer." + content);
        }
    }

    /**
     * Cannot be nul, can be  a activitySubCategory or a question using a list composed to  activitySubCategory element
     */

    public void controlActivityType(String content, BAD bad) {

        if (content == null || content.length() == 0) {
            logLine.addError("There is no activityType : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        //test if the activityType is a code
        if (controlList(ActivityTypeCode.class, content)) {
            logLine.addDebug("There is a activityType and it's an ActivitySubCategoryCode");

            bad.setActivityType("ActivityTypeCode." + content);

        }
        else {

            Matcher matcher = questionPattern.matcher(content);

            if (matcher.find()) {


                if (controlList(QuestionCode.class, matcher.group(1))) {

                    //load the question
                    Question question = questionService.findByCode(new QuestionCode(matcher.group(1)));

                    if (question instanceof ValueSelectionQuestion) {
                        if (!codeConversionService.isSublistOf(((ValueSelectionQuestion) question).getCodeList(), CodeList.ActivityType)) {

                            HashMap<String, CodeLabel> list = codeLabelService.findCodeLabelsByList(((ValueSelectionQuestion) question).getCodeList());
                            if (codeConversionService.toActivityTypeCode(new Code(((ValueSelectionQuestion) question).getCodeList(), new ArrayList<CodeLabel>(list.values()).get(0).getKey())) == null) {
                                logLine.addError("ActivitySubCategory is a ValueSelectionQuestion but this list is not a (sub)list of ActivitySubCategory or convertible");
                                return;
                            }

                        }
                    } else {
                        logLine.addError("ActivityType is a question but not a ValueSelectionQuestion");
                    }

                    String value = "toActivityTypeCode(question" + matcher.group(1) + "Answer)";

                    //test the criteron conversion
                    if (matcher.group(2) != null) {
                        value = "convertCode(" + value + ", " + matcher.group(2) + "," + ActivityTypeCode.class.getName() + ")";
                    }

                    //add to bad
                    bad.setActivityType(value);
                    bad.addQuestion(matcher.group(1));
                    return;
                }

            }
            logLine.addError("There is a activityType but it's not an ActivityTypeCode or an answer." + content);
        }

        /* TODO remove

        else if (controlList(QuestionCode.class, content)) {

            //load the question
            Question question = questionService.findByCode(new QuestionCode(content));

            if (question instanceof ValueSelectionQuestion) {
                if (!codeConversionService.isSublistOf(((ValueSelectionQuestion) question).getCodeList(), CodeList.ActivityType)) {

                    HashMap<String, CodeLabel> list = codeLabelService.findCodeLabelsByList(((ValueSelectionQuestion) question).getCodeList());
                    if (codeConversionService.toActivityTypeCode(new Code(((ValueSelectionQuestion) question).getCodeList(), new ArrayList<CodeLabel>(list.values()).get(0).getKey())) == null) {
                        logLine.addError("ActivitySubCategory is a ValueSelectionQuestion but this list is not a (sub)list of ActivitySubCategory or convertible");
                        return;
                    }
                }
            } else {
                logLine.addError("ActivitySubCategory is a question but not a ValueSelectionQuestion");
                return;
            }

            //add to bad
            bad.setActivityType("toActivityTypeCode(question" + content + "Answer)");
            bad.addQuestion(content);

        } else {
            logLine.addError("There is a activityType but it's not an activityType or an answer." + content);
        }
        */
    }

    /**
     * Cannot be nul, can be  a activitySubCategory or a question using a list composed to  activitySubCategory element
     */
    public void controlActivitySource(String content, BAD bad) {


        if (content == null || content.length() == 0) {
            logLine.addError("There is no activitySource : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        //test if the activitySubCategory is a code
        if (controlList(ActivitySourceCode.class, content)) {
            logLine.addDebug("There is a activitySource and it's an ActivitySubCategoryCode");

            bad.setActivitySource("ActivitySourceCode." + content);

        } else {

            Matcher matcher = questionPattern.matcher(content);

            if (matcher.find()) {


                if (controlList(QuestionCode.class, matcher.group(1))) {

                    //load the question
                    Question question = questionService.findByCode(new QuestionCode(matcher.group(1)));

                    if (question instanceof ValueSelectionQuestion) {
                        if (!codeConversionService.isSublistOf(((ValueSelectionQuestion) question).getCodeList(), CodeList.ActivitySource)) {

                            HashMap<String, CodeLabel> list = codeLabelService.findCodeLabelsByList(((ValueSelectionQuestion) question).getCodeList());
                            if (codeConversionService.toActivitySourceCode(new Code(((ValueSelectionQuestion) question).getCodeList(), new ArrayList<CodeLabel>(list.values()).get(0).getKey())) == null) {
                                logLine.addError("ActivitySubCategory is a ValueSelectionQuestion but this list is not a (sub)list of ActivitySubCategory or convertible");
                                return;
                            }
                        }
                    } else {
                        logLine.addError("ActivitySource is a question but not a ValueSelectionQuestion");
                    }

                    String value = "toActivitySourceCode(question" + matcher.group(1) + "Answer)";

                    //test the criteron conversion
                    if (matcher.group(2) != null) {
                        //TODO control criterion
                        value = "convertCode(" + value + ", ConversionCriterion." + matcher.group(3) + ")";
                    }

                    //add to bad
                    bad.setActivitySource(value);
                    bad.addQuestion(matcher.group(1));
                    return;
                }

            }
            logLine.addError("There is a activitySource but it's not an ActivitySourceCode or an answer." + content);
        }

        /* TODO remove
        else if (controlList(QuestionCode.class, content)) {

            //load the question
            Question question = questionService.findByCode(new QuestionCode(content));

            if (question instanceof ValueSelectionQuestion) {
                if (!codeConversionService.isSublistOf(((ValueSelectionQuestion) question).getCodeList(), CodeList.ActivitySource)) {

                    HashMap<String, CodeLabel> list = codeLabelService.findCodeLabelsByList(((ValueSelectionQuestion) question).getCodeList());
                    if (codeConversionService.toActivitySourceCode(new Code(((ValueSelectionQuestion) question).getCodeList(), new ArrayList<CodeLabel>(list.values()).get(0).getKey())) == null) {
                        logLine.addError("ActivitySubCategory is a ValueSelectionQuestion but this list is not a (sub)list of ActivitySubCategory or convertible");
                        return;
                    }
                }
            } else {
                logLine.addError("ActivitySubCategory is a question but not a ValueSelectionQuestion");
            }

            //add to bad
            bad.setActivitySource("toActivitySourceCode(question" + content + "Answer)");
            bad.addQuestion(content);

        } else {
            logLine.addError("There is a activitySource but it's not an activityType or an answer." + content);
        }
        */
    }

    /**
     * can be null. Boolean expected or BooleanQuestion type
     */
    public void controlActivityOwnerShip(String content, BAD bad) {

        boolean activityOwnerShipValid = false;
        if (content == null) {
            logLine.addInfo("ActivityOwnerShip is null");
        } else {

            //try to convert to boolean
            if (controlBoolean(content) != null) {
                bad.setActivityOwnership(controlBoolean(content));
                return;
            }

            //try to convert to BooleanQuestion
            if (controlList(QuestionCode.class, content)) {
                if (controlQuestionType(content, BooleanQuestion.class)) {

                    //add to bad
                    bad.setActivityOwnership("toBoolean(question" + content + "Answer)");
                    bad.addQuestion(content);

                }
            } else {
                bad.setActivityOwnership(controlCondition(bad, content, "ActivityOwnership"));

            }
        }
    }

    /**
     * cannot be null. Except a unitCode
     */
    public void controlUnit(String content, BAD bad) {
        if (content == null || content.length() == 0) {
            logLine.addError("There is no unit : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }
        //test if the activityType is a code
        if (!controlList(UnitCode.class, content)) {
            logLine.addError("This is not a unit code : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        //load unit
        Unit unit = unitService.findByCode(new UnitCode(content));
        bad.setUnit(unit);
    }

    /**
     * Cannot be null
     */
    public void controlValue(String content, BAD bad) {

        if (content == null || content.length() == 0) {
            logLine.addError("There is no value : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        //control and create
        bad.setValue(controlEquation(bad, content, "value"));
    }


    public void controlCondition(String content, BAD bad) {
        if (content != null && content.length() != 0) {

            bad.setCondition(controlCondition(bad, content, "Condition"));
        }
    }

    /**
     * control the value of the question for the test
     *
     * @param question
     * @param content
     * @return
     */
    public Answer controlAnswerValue(Question question, String content) {

        //create answer
        Answer answer = new Answer(question);

        //control if the question needs repetition and witch
        List<QuestionSet> questionSetsRepetable = new ArrayList<>();
        getAllRepetitionQuestionSet(question.getQuestionSet(), questionSetsRepetable);

        //remove space characters
        content = content.replaceAll("( | )", "");

        //take answer one by one
        Pattern pattern = Pattern.compile("(\\(([^\\(\\)]*)\\))?([^;]+)(;|$)");

        Pattern patternRepetition = Pattern.compile("(A[A-Z]*[0-9]+):([0-9]+)(;|$)");

        Pattern patternNumeric = Pattern.compile("^([0-9.]+)(\\[([^\\[\\]]+?)\\])?");

        Matcher m = pattern.matcher(content);

        while (m.find()) {

            String value = m.group(3);
            Unit unit = null;
            Object valueToAdd = null;


            //control answer by question type
            if (question instanceof NumericQuestion) {

                //remove point
                value = value.replace(",", ".");

                //detect unit
                Matcher mNum = patternNumeric.matcher(value);

                if (mNum.find()) {

                    //control unit
                    if (((NumericQuestion) question).getUnitCategory() != null) {

                        //if there is not unit defined
                        if (mNum.group(3) == null) {

                            //... use the default unit of the question
                            if (((NumericQuestion) question).getDefaultUnit() != null) {
                                unit = ((NumericQuestion) question).getDefaultUnit();
                            }
                            //... or the main unit of the unit category
                            else {
                                unit = ((NumericQuestion) question).getUnitCategory().getMainUnit();
                            }

                            logLine.addInfo("QuestionValue : cannot found a unit for the question " + question.getCode().getKey() + " : the default unit " + unit.getSymbol() + " will be used");
                        } else {
                            //try to use the defined unit
                            unit = controlUnitCategory(((NumericQuestion) question), mNum.group(3), null, "questionValue");

                            if (unit == null) {
                                logLine.addError("QuestionValue : cannot found a unit for the question " + question.getCode().getKey());
                                continue;
                            }
                        }
                    }

                    //convert the value (without the unit) into double
                    try {
                        Double valueDouble = Double.parseDouble(mNum.group(1));
                        valueToAdd = String.valueOf(valueDouble);
                    } catch (NumberFormatException e) {
                        logLine.addError("QuestionValue : Number expected but conversion failed");
                        continue;
                    }
                } else {
                    logLine.addError("QuestionValue : value must be a numeric (optinaly with a unit) but not found : " + value);
                    continue;
                }

            } else if (question instanceof ValueSelectionQuestion) {
                //if it's a number, convert to integer
                try {
                    Double valueD = Double.parseDouble(value);
                    value = valueD.intValue() + "";
                } catch (NumberFormatException e) {
                }


                if (!controlListElement(((ValueSelectionQuestion) question).getCodeList(), value)) {
                    logLine.addError("QuestionValue : value must be a member of the list " + ((ValueSelectionQuestion) question).getCodeList().name() + " but not found : " + value);
                    continue;
                }
                valueToAdd = "\"" + value + "\"";
            } else if (question instanceof BooleanQuestion) {
                if (controlBoolean(value) == null) {
                    logLine.addError("QuestionValue : boolean expected but not found : " + value);
                    continue;
                }
                if (controlBoolean(value).equals("true")) {
                    valueToAdd = "\"1\"";
                } else {
                    valueToAdd = "\"0\"";
                }
            } else {
                valueToAdd = "\"" + value + "\"";
            }

            //add value
            //create a answer for each answer founded
            AnswerLine answerLine = new AnswerLine(valueToAdd);
            if (unit != null) {
                answerLine.setUnit(unit);
            }

            //control repetition
            Map<String, Integer> repetitionMap = new HashMap<>();

            //if the group(2) is null not, there is some repetition
            if (m.group(2) != null) {

                Matcher m2 = patternRepetition.matcher(m.group(2));
                while (m2.find()) {
                    repetitionMap.put(m2.group(1), Integer.parseInt(m2.group(2)));
                }
            }

            //control repetition
            if (repetitionMap.size() != questionSetsRepetable.size()) {
                //TODO boost the control
                logLine.addError("Wrong repetition map");
                continue;
            }
            //add repetition map
            answerLine.setMapRepetition(repetitionMap);

            //add line to answer
            answer.addAnswerLine(answerLine);
        }
        return answer;
    }

    /**
     * Control the value of the test for the bad
     *
     * @param content
     * @param bad
     */
    public void controlTestValue(String content, BAD bad) {

        //control test
        if (content == null || content.length() == 0) {
            logLine.addWarn("There is not test");
            return;
        }

        //remove space characters
        content = content.replaceAll("( | )", "");

        //take answer one by one
        Pattern pattern = Pattern.compile("(\\(([^\\(\\)]*)\\))?([^;]+)(;|$)");

        Pattern patternRepetition = Pattern.compile("(A[A-Z]*[0-9]+):([0-9]+)(;|$)");

        Matcher m = pattern.matcher(content);

        while (m.find()) {

            //control value
            String value = m.group(3).replace(",", ".");
            Double valueDouble;
            try {
                valueDouble = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                logLine.addError("TestValue : expected number but conversion failed : " + value);
                continue;
            }

            //add value
            //create a answer for each answer founded
            BadTestValue badTestValue = new BadTestValue(valueDouble);


            //control repetition
            Map<String, Integer> repetitionMap = new HashMap<>();

            //if the group(2) is null not, there is some repetition
            if (m.group(2) != null) {

                Matcher m2 = patternRepetition.matcher(m.group(2));
                while (m2.find()) {
                    repetitionMap.put(m2.group(1), Integer.parseInt(m2.group(2)));
                }
            }

            //control repetition
            /*
            if (repetitionMap.size() != questionSetsRepetable.size()) {
                //TODO boost the control
                logLine.addError("Wrong repetition map");
                continue;
            }
            */
            //add repetition map
            badTestValue.setMapRepetition(repetitionMap);

            //add line to answer
            bad.addTestValue(badTestValue);
        }
    }


    /* ********************************
    ***************** PRIVATE ***********
    ********************************** */


    private String convertToRegex(String s) {
        return s.replace(".", "\\.").replace("[", "\\[").replace("]", "\\]").replace("(", "\\(").replace(")", "\\)");


    }

    private void evaluateFormula(String formula) throws Exception {

        //convert , to .
        formula = formula.replaceAll(",", ".");

        Double result;
        try {
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(formula);
            result = expression.getValue(Double.class);
        } catch (ParseException | NullPointerException | SpelEvaluationException e1) {
            throw new Exception(e1);
        }
        if (result == null) {
            throw new Exception("Result is null");
        }
    }

    private boolean evaluateCondition(String formula) {

        //convert , to .
        formula = formula.replaceAll(",", ".");

        Boolean result = null;
        try {
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(formula);
            result = expression.getValue(Boolean.class);
        } catch (ParseException | NullPointerException e1) {
            return false;
        }
        if (result == null) {
            return false;
        }

        return true;
    }


    private boolean controlList(Class classToTest, String code) {

        for (Field field : classToTest.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * find by codeLabel
     *
     * @param codeList
     * @param code
     * @return
     */
    private boolean controlListElement(CodeList codeList, String code) {

        CodeLabel codeLabel = codeLabelService.findCodeLabelByCode(new Code(codeList, code));
        if (codeLabel != null) {
            return true;
        }
        return false;
    }


    private <T extends Question> boolean controlQuestionType(String questionCode, Class<T> questionClass) {

        Question question = questionService.findByCode(new QuestionCode(questionCode));

        if (questionClass.isInstance(question)) {
            return true;
        }
        logLine.addDebug("class founded : " + question.getClass() + " , expected " + questionClass);
        return false;
    }


    private static String controlBoolean(String s) {
        if (s != null) {
            if (s.equals("1") || s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes")) {
                return "true";
            } else if (s.equals("0") || s.equals("false") || s.equalsIgnoreCase("no")) {
                return "false";
            }
        }
        return null;
    }

    private String controlCondition(BAD bad, String content, String type) {

        String condition = content;
        String value = content;

        // 1) find all question
        String patternString = "(A[A-Z]*[0-9]+)(\\[(.+?)\\])?( *(==|!=|<|>|<=|>=) *([A-Za-z0-9_.]+|true|false))?";

        //group(1) => questionCodeKey
        //group(2) => unit with [] (optional)
        //group(3) => unit (optional)
        //group(4) => comparison group (optional)
        //group(5) operator
        //group(6) comparison  member

        StringBuffer sb = new StringBuffer();

        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {

            String questionCodeKey = matcher.group(1);

            // a) test question
            if (!controlList(QuestionCode.class, questionCodeKey)) {
                logLine.addError("The " + type + " contains a questionCode unknown : " + questionCodeKey);
                return questionCodeKey;

            } else {

                Question question = questionService.findByCode(new QuestionCode(questionCodeKey));

                //add question to the bad
                bad.addQuestion(question.getCode().getKey());

                // b) control questionType
                if (question instanceof NumericQuestion || question instanceof BooleanQuestion || question instanceof ValueSelectionQuestion) {

                    // c) control unit if it's needed
                    if (question instanceof NumericQuestion) {
                        controlUnitCategory(((NumericQuestion) question), matcher.group(3), bad.getUnit().getCategory(), type);
                    }

                    // d) control comparison member
                    //String patternStringEquation = "((" + convertToRegex(questionCodeKey) + ") *(==|!=|<|>|<=|>=) *)([A-Za-z0-9_]+|true|false)";
                    //Pattern patternEquation = Pattern.compile(patternStringEquation);

                    //Matcher matcherEquation = patternEquation.matcher(content);

                    String comparison = matcher.group(4);
                    String comparisonMember = null;
                    String operator = null;
                    String questionValue = matcher.group(0);

                    //there is a member comparison
                    if (comparison != null) {


                        comparisonMember = matcher.group(6);
                        operator = matcher.group(5);
                        questionValue = matcher.group(0);

                        //control by questionType
                        if (question instanceof ValueSelectionQuestion) {

                            //for valueSelectionQuestion => expected a code from the same list than the question
                            if (!controlListElement(((ValueSelectionQuestion) question).getCodeList(), comparisonMember)) {
                                logLine.addError(type + " : comparison member (" + comparisonMember + ") is not an element of the list : " + ((ValueSelectionQuestion) question).getCodeList().name() + " for question " + questionCodeKey + "/ equation : " + content);
                            } else {


                                //convert operator
                                if (operator.equals("!=")) {

                                    //create value
                                    questionValue = "!getCode(question" + questionCodeKey + "Answer).getKey().equals(\"" + comparisonMember + "\")";

                                } else if (operator.equals("==")) {

                                    //create value
                                    questionValue = "getCode(question" + questionCodeKey + "Answer).getKey().equals(\"" + comparisonMember + "\")";

                                } else {

                                    //operator invalid : error !
                                    logLine.addError(type + " : the operator " + operator + " is not valid for the comparison " + matcher.group() + " because thr question is a codeList (== or != only accepted)");
                                }
                            }


                        }
                        //control booleanQuestion
                        else if (question instanceof BooleanQuestion) {

                            if (comparisonMember.equals("1") || comparisonMember.equalsIgnoreCase("true") || comparisonMember.equalsIgnoreCase("yes")) {
                                questionValue = "toBoolean(question" + questionCodeKey + "Answer) == true";
                            } else if (comparisonMember.equals("0") || comparisonMember.equalsIgnoreCase("false") || comparisonMember.equalsIgnoreCase("no")) {
                                questionValue = "toBoolean(question" + questionCodeKey + "Answer) == false";
                            } else {
                                logLine.addError(type + " : the comparison member (" + comparisonMember + ") is not compatible with the BooleanQuestion type for the question : " + questionCodeKey);
                            }

                        }
                        //other question type are not accepted
                        else {
                            logLine.addError(type + " : the questionType " + question.getClass() + " of the question " + questionCodeKey + " is not currently compatible with condition");
                        }
                    } else {
                        //boolean ?
                        if (question instanceof BooleanQuestion) {
                            questionValue = "toBoolean(question" + questionCodeKey + "Answer)";
                        } else {
                            logLine.addError(type + " : cannot found the other member of the comparison : " + content + " for question " + questionCodeKey);
                        }
                    }

                    // e) replace
                    //replace comparison element
                    if (comparisonMember != null) {
                        matcher.appendReplacement(sb, questionValue);

                        //replace into condition
                        condition = condition.replaceAll(convertToRegex(matcher.group()), "true");
                    } else {
                        matcher.appendReplacement(sb, questionValue);

                        //replace into condition
                        condition = condition.replaceAll(convertToRegex(matcher.group()), "true");
                    }


                } else {
                    logLine.addError(type + " : " + question.getClass() + " aren't supported (question " + questionCodeKey + ")");
                }
            }
        }

        matcher.appendTail(sb);

        value = sb.toString();

        //control euqation
        try {
            evaluateCondition(condition);
        } catch (Exception e) {
            logLine.addError("The " + type + " cannot be convert to condition: " + condition + " (" + content + ") =>" + e.getMessage());
        }
        return value;
    }

    /**
     * control equation
     * all members must be convertible to double : questionType accepted are only NumericQuestion
     * Step :
     * 1) find and treat all questions
     * steps for each question :
     * a) find question by code
     * b) control the questionType
     * c) control unit if it's needed
     * d) replace code
     * 2) control equation with replace elements
     */
    private String controlEquation(BAD bad, String content, String type) {

        //create an equation test
        String equation = content;
        String value = content;

        //1) find all question
        String patternString = "(A[A-Z]*[0-9]+)(\\[(.+?)\\])?";
        Pattern pattern = Pattern.compile(patternString);
        StringBuffer sb = new StringBuffer();

        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {

            String questionCodeKey = matcher.group(1);

            //a) find question by code
            //control if the questionCode if a part of the questionList
            if (!controlList(QuestionCode.class, questionCodeKey)) {

                //if the questionCode aren't into the questionList, it's an error
                logLine.addError("The " + type + " contains a questionCode unknown : " + questionCodeKey);
                return questionCodeKey;
            } else {

                //load question
                Question question = questionService.findByCode(new QuestionCode(questionCodeKey));

                //add question to the bad
                bad.addQuestion(question.getCode().getKey());

                UnitCategory unitCategoryQuestion = null;
                Unit unit = null;

                //b) control the questionType
                if (question instanceof NumericQuestion) {
                    //load the unitCategory
                    unitCategoryQuestion = ((NumericQuestion) question).getUnitCategory();

                    //c) control unit if it's needed
                    if (unitCategoryQuestion != null) {
                        unit = controlUnitCategory(((NumericQuestion) question), matcher.group(3), bad.getUnit().getCategory(), type);
                    }

                } else {
                    //other question type are not accepted
                    logLine.addError("The " + type + " contains a questionCode (" + questionCodeKey + ") but it's not q DoubleQuestion or IntegerQuestion or PercentageQuestion, but : " + question.getClass());
                }

                //d) replace code
                if (unitCategoryQuestion != null) {
                    if (unit != null) {
                        matcher.appendReplacement(sb, "toDouble(question" + questionCodeKey + "Answer, getUnitByCode(UnitCode." + unit.getUnitCode().getKey() + "))");
                    } else {
                        matcher.appendReplacement(sb, "toDouble(question" + questionCodeKey + "Answer, baseActivityDataUnit)");
                    }
                } else {
                    matcher.appendReplacement(sb, "toDouble(question" + questionCodeKey + "Answer)");

                }

                // replace into equation
                equation = equation.replaceAll(convertToRegex(matcher.group()), "1");
            }
        }

        matcher.appendTail(sb);

        value = sb.toString();

        // 2) control equation with replace elements
        try {
            evaluateFormula(equation);
        } catch (Exception e) {
            logLine.addError("The " + type + " cannot be convert to equation: " + equation + " (" + content + ") =>" + e.getMessage());
        }

        //replace , by .  in number
        String patternPuntString = "([0-9]+),([0-9]+)";
        Pattern patternPunt = Pattern.compile(patternPuntString);

        Matcher matcherPunt = patternPunt.matcher(content);

        while (matcherPunt.find()) {
            value = value.replace(matcherPunt.group(), matcherPunt.group(1) + "." + matcherPunt.group(2));
        }

        //return value
        return value;
    }

    /**
     * control unit from question
     *
     * @param question            the question
     * @param unitExpected        the unit used to convert the answer of the question
     * @param unitCategoryDefault if the unitExpected if null, use the unitCategoryDefault to control equivalence
     * @param type                the field controlled
     * @return a unit if the equivalence is true and the equation need a unit to convert the answer. null if not
     * Complete also the log for error and / or info
     */
    private Unit controlUnitCategory(NumericQuestion question, String unitExpected, UnitCategory
            unitCategoryDefault, String type) {

        //if the question doesn't have unitCategory, control useless
        if (question.getUnitCategory() != null) {

            //if there is not unit expected
            if (unitExpected == null || unitExpected.length() == 0) {

                //... continue with the unit cagtegory default
                if (unitCategoryDefault != null) {

                    //control equivalence between BAD unit.unitCat and question.unitCat
                    if (!question.getUnitCategory().equals(unitCategoryDefault)) {
                        //categories != => error !
                        logLine.addError("The " + type + " contains a questionCode without unit specified and the unitCategory of the question doesn't correspond to the unitCategory of the BAD : " + question.getCode().getKey());
                    } else {
                        //ok => info
                        logLine.addInfo("The " + type + " contains a questionCode without unit specified, but the unitCat is the same than the BAD");
                    }
                } else {
                    // if there is not unit expected or unitCategory default, this is an error
                    logLine.addInfo("The " + type + " contains a questionCode without unit specified");
                }
                //there is a unit expected => control it
            } else {

                //test unit expected
                if (!controlList(UnitCode.class, unitExpected)) {
                    logLine.addError("The " + type + " a questionCode with unit specified, but this unit was not found : " + unitExpected);
                } else {

                    //load unit
                    Unit unit = unitService.findByCode(new UnitCode(unitExpected));

                    //test unit
                    if (!question.getUnitCategory().equals(unit.getCategory())) {
                        logLine.addError("The " + type + " contains a questionCode, but the specified unit do not " +
                                "come from the unitCategory of the question : " + question.getCode().getKey() + ", unitCategory of the question : " + question.getUnitCategory() + ", unitCategory of the unit : " + unit.getCategory());
                    } else {
                        return unit;
                    }
                }
            }
        }
        return null;
    }


    private void getAllRepetitionQuestionSet(QuestionSet questionSet, List<QuestionSet> questionSetsRepetable) {


        if (questionSet.getRepetitionAllowed()) {
            questionSetsRepetable.add(questionSet);
        }

        if (questionSet.getParent() != null) {
            getAllRepetitionQuestionSet(questionSet.getParent(), questionSetsRepetable);
        }

    }
}
