package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.type.*;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.CodeConversionService;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.QuestionService;
import eu.factorx.awac.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.expression.ParseException;
import play.Logger;

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


    BADLog badLog;

    public void setBadLog(BADLog badLog) {
        this.badLog = badLog;
    }

    /**
     * cannot be null
     * control BADKey
     * => look into BaseActivityDataCode to found the code
     * return the content
     */
    public void controlBADKey(String cellContent, int line, BAD bad) {

        String badKey = cellContent;
        if (badKey == null || badKey.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no badKey : " + cellContent);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        if (!controlList(BaseActivityDataCode.class, badKey)) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "This is not a badKey : " + cellContent);

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
    public void controlName(String content, int line, BAD bad) {

        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no name: " + content);

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
    public void controlRank(String content, int line, BAD bad) {

        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.WARNING, line, "There rank is null");

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }
        try {
            bad.setRank(Integer.parseInt(content));
        } catch (NumberFormatException e) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "The rank is not null but it's not a valid number  : " + content);
        }

        //null => cannot be generated
        bad.setCanBeGenerated(false);
        return;
    }

    /**
     * Can be an answer or a string or can be null
     */
    public void controlSpecificPurpose(String content, int line, BAD bad) {
        if (content != null && content.length() > 0) {

            //test if the specific purpose is a code
            //control if the question is a text
            if (controlList(QuestionCode.class, content)) {
                if (controlQuestionType(content, StringQuestion.class)) {
                    badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a SpecificPurpose and it's a Stringquestion");

                    bad.setSpecificPurpose("toString(question" + content + "Answer)");
                    bad.addQuestion(content);

                } else {
                    badLog.addToLog(BADLog.LogType.ERROR, line, "SpecificPurpose : it's a question but not a StringQuestion : SpecificPurpose will be null");
                }
            } else {
                badLog.addToLog(BADLog.LogType.WARNING, line, "SpecificPurpose is a string");
                bad.setSpecificPurpose("\"" + content + "\"");
            }
        } else {
            badLog.addToLog(BADLog.LogType.WARNING, line, "The SpecificPurpose is null");
        }
    }

    /**
     * Cannot be nul, always a activityCategory
     */
    public void controlActivityCategory(String content, int line, BAD bad) {

        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no activityCategory : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        //save all data into one object
        if (!controlList(ActivityCategoryCode.class, content)) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "This activityCategory was not found : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }
        bad.setActivityCategoryCode("ActivityCategoryCode." + content);
    }

    /**
     * Cannot be nul, can be  a activitySubCategory or a question using a list composed to  activitySubCategory element or
     * //TODO more complex
     */
    public void controlActivitySubCategory(String content, int line, BAD bad) {

        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no activitySubCategory : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }
        //test if the activitySubCategory is a code
        if (controlList(ActivitySubCategoryCode.class, content)) {
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activitySubCategory and it's an ActivitySubCategoryCode");

            //add to bad
            bad.setActivitySubCategory("ActivitySubCategoryCode." + content);

        } else if (controlList(QuestionCode.class, content)) {

            //load the question
            Question question = questionService.findByCode(new QuestionCode(content));

            if (question instanceof ValueSelectionQuestion) {
                if (!codeConversionService.isSublistOf(((ValueSelectionQuestion) question).getCodeList(), CodeList.ActivitySubCategory)) {
                    badLog.addToLog(BADLog.LogType.ERROR, line, "ActivitySubCategory is a ValueSelectionQuestion but this list is not a (sub)list of ActivitySubCategory");
                }
            } else {
                badLog.addToLog(BADLog.LogType.ERROR, line, "ActivitySubCategory is a question but not a ValueSelectionQuestion");
            }

            //add to bad
            bad.setActivitySubCategory("toActivitySubCategoryCode(question" + content + "Answer)");
            bad.addQuestion(content);

        } else {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is a activitySubCategory but it's not an ActivitySubCategoryCode or an answer." + content);
        }


    }

    /**
     * Cannot be nul, can be  a activitySubCategory or a question using a list composed to  activitySubCategory element or
     * //TODO more complex
     */
    public void controlActivityType(String content, int line, BAD bad) {

        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no activityType : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        //test if the activityType is a code
        if (controlList(ActivityTypeCode.class, content)) {
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activityType and it's an ActivitySubCategoryCode");

            bad.setActivityType("ActivityTypeCode." + content);

        } else if (controlList(QuestionCode.class, content)) {

            //load the question
            Question question = questionService.findByCode(new QuestionCode(content));

            if (question instanceof ValueSelectionQuestion) {
                if (!codeConversionService.isSublistOf(((ValueSelectionQuestion) question).getCodeList(), CodeList.ActivityType)) {
                    badLog.addToLog(BADLog.LogType.ERROR, line, "ActivitySubCategory is a ValueSelectionQuestion but this list is not a (sub)list of ActivitySubCategory");
                }
            } else {
                badLog.addToLog(BADLog.LogType.ERROR, line, "ActivitySubCategory is a question but not a ValueSelectionQuestion");
            }

            //add to bad
            bad.setActivityType("toActivityTypeCode(question" + content + "Answer)");
            bad.addQuestion(content);

        } else {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is a activityType but it's not an activityType or an answer." + content);
        }
    }

    /**
     * Cannot be nul, can be  a activitySubCategory or a question using a list composed to  activitySubCategory element or
     * //TODO more complex
     */
    public void controlActivitySource(String content, int line, BAD bad) {


        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no activitySource : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        //test if the activitySubCategory is a code
        if (controlList(ActivitySourceCode.class, content)) {
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activitySource and it's an ActivitySubCategoryCode");

            bad.setActivitySource("ActivitySourceCode." + content);

        } else if (controlList(QuestionCode.class, content)) {

            //load the question
            Question question = questionService.findByCode(new QuestionCode(content));

            if (question instanceof ValueSelectionQuestion) {
                if (!codeConversionService.isSublistOf(((ValueSelectionQuestion) question).getCodeList(), CodeList.ActivitySource)) {
                    badLog.addToLog(BADLog.LogType.ERROR, line, "ActivitySubCategory is a ValueSelectionQuestion but this list is not a (sub)list of ActivitySubCategory");
                }
            } else {
                badLog.addToLog(BADLog.LogType.ERROR, line, "ActivitySubCategory is a question but not a ValueSelectionQuestion");
            }

            //add to bad
            bad.setActivitySource("toActivitySourceCode(question" + content + "Answer)");
            bad.addQuestion(content);

        } else {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is a activitySource but it's not an activityType or an answer." + content);
        }
    }

    /**
     * can be null. Boolean expected or BooleanQuestion type
     */
    public void controlActivityOwnerShip(String content, int line, BAD bad) {

        boolean activityOwnerShipValid = false;
        if (content == null) {
            badLog.addToLog(BADLog.LogType.WARNING, line, "ActivityOwnerShip is null");
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
                bad.setActivityOwnership(controlCondition(bad, content, line, "ActivityOwnership"));

            }
        }
    }

    /**
     * cannot be null. Except a unitCode
     */
    public void controlUnit(String content, int line, BAD bad) {
        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no unit : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }
        //test if the activityType is a code
        if (!controlList(UnitCode.class, content)) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "This is not a unit code : " + content);

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
    public void controlValue(String content, int line, BAD bad) {

        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no value : " + content);

            //null => cannot be generated
            bad.setCanBeGenerated(false);
            return;
        }

        //control and create
        bad.setValue(controlEquation(bad, content, line, "value"));
    }

    /**
     * TODO control equation
     */
    public void controlCondition(String content, int line, BAD bad) {
        if (content != null && content.length() != 0) {

            bad.setCondition(controlCondition(bad, content, line, "Condition"));
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

        Double result = null;
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
        badLog.addToLog(BADLog.LogType.DEBUG, 0, "class founded : " + question.getClass() + " , expected " + questionClass);
        return false;
    }


    private static String controlBoolean(String s) {
        if (s != null) {
            if (s.equals("1") || s.equals("true")) {
                return "true";
            } else if (s.equals("0") || s.equals("false")) {
                return "false";
            }
        }
        return null;
    }

    private String controlCondition(BAD bad, String content, int line, String type) {

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


        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {

            Logger.info("|"+matcher.group()+"|"+matcher.group(3)+"|"+matcher.group(4)+"|"+matcher.group(5)+"|");


            String questionCodeKey = matcher.group(1);

            // a) test question
            if (!controlList(QuestionCode.class, questionCodeKey)) {
                badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " contains a questionCode unknown : " + questionCodeKey);
                return questionCodeKey;

            } else {

                Question question = questionService.findByCode(new QuestionCode(questionCodeKey));

                //add question to the bad
                bad.addQuestion(question.getCode().getKey());

                // b) control questionType
                if (question instanceof NumericQuestion || question instanceof BooleanQuestion || question instanceof ValueSelectionQuestion) {

                    // c) control unit if it's needed
                    if (question instanceof NumericQuestion) {
                        controlUnitCategory(((NumericQuestion) question), matcher.group(3), bad.getUnit().getCategory(), line, type);
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
                                badLog.addToLog(BADLog.LogType.ERROR, line, type + " : comparison member (" + comparisonMember + ") is not an element of the list : " + ((ValueSelectionQuestion) question).getCodeList().name() + " for question " + questionCodeKey + "/ equation : " + content);
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
                                    badLog.addToLog(BADLog.LogType.ERROR, line, type + " : the operator " + operator + " is not valid for the comparison " + matcher.group() + " because thr question is a codeList (== or != only accepted)");
                                }
                            }


                        }
                        //control booleanQuestion
                        else if (question instanceof BooleanQuestion) {

                            if (comparisonMember.equals("1") || comparisonMember.equals("true")) {
                                questionValue = "toBoolean(question" + questionCodeKey + "Answer) == true";
                            } else if (comparisonMember.equals("0") || comparisonMember.equals("false")) {
                                questionValue = "toBoolean(question" + questionCodeKey + "Answer) == false";
                            } else {
                                badLog.addToLog(BADLog.LogType.ERROR, line, type + " : the comparison member (" + comparisonMember + ") is not compatible with the BooleanQuestion type for the question : " + questionCodeKey);
                            }

                        }
                        //other question type are not accepted
                        else {
                            badLog.addToLog(BADLog.LogType.ERROR, line, type + " : the questionType " + question.getClass() + " of the question " + questionCodeKey + " is not currently compatible with condition");
                        }
                    } else {
                        //boolean ?
                        if (question instanceof BooleanQuestion) {
                            questionValue = "toBoolean(question" + questionCodeKey + "Answer)";
                        } else {
                            badLog.addToLog(BADLog.LogType.ERROR, line, type + " : cannot found the other member of the comparison : " + content + " for question " + questionCodeKey);
                        }
                    }

                    // e) replace
                    //replace comparison element
                    if (comparisonMember != null) {
                        value = value.replaceAll(convertToRegex(matcher.group()), questionValue);

                        //replace into condition
                        condition = condition.replaceAll(convertToRegex(matcher.group()), "true");
                    } else {
                        Logger.info(value+" "+matcher.group()+" "+questionValue);
                        value = value.replaceAll(convertToRegex(matcher.group()), questionValue);

                        //replace into condition
                        condition = condition.replaceAll(convertToRegex(matcher.group()), "true");
                    }


                } else {
                    badLog.addToLog(BADLog.LogType.ERROR, line, type + " : " + question.getClass() + " aren't supported (question " + questionCodeKey + ")");
                }
            }
        }

        //control euqation
        try {
            evaluateCondition(condition);
        } catch (Exception e) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " cannot be convert to condition: " + condition + " (" + content + ") =>" + e.getMessage());
        }
        return value;
    }

    /**
     * @param line
     * @param questionCodeKey
     * @param unitExpected
     * @param unitCategoryExpected
     * @return
     */
/*
    private String controlCondition(int line, String questionCodeKey, String unitExpected, UnitCategory
            unitCategoryExpected, String type) {

        //test question
        if (!controlList(QuestionCode.class, questionCodeKey)) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " contains a questionCode unknown : " + questionCodeKey);
            return questionCodeKey;
        } else {

            //load question
            Question question = questionService.findByCode(new QuestionCode(questionCodeKey));
            UnitCategory unitCategoryQuestion = null;
            Unit unit = null;

            //question type => accpet DoubleQuestion, IntegerQuestion or PercentageQuestion
            //get the unitCategory expected by the question

            if (question instanceof DoubleQuestion) {
                if (((DoubleQuestion) question).getUnitCategory() != null) {
                    unitCategoryQuestion = ((DoubleQuestion) question).getUnitCategory();
                }
            } else if (question instanceof IntegerQuestion) {
                if (((IntegerQuestion) question).getUnitCategory() != null) {
                    unitCategoryQuestion = ((IntegerQuestion) question).getUnitCategory();
                }
            } else if (question instanceof PercentageQuestion) {
                //no unit
            } else {
                //for equation : do  not accept an other type of question
                if (equation) {
                    badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " contains a questionCode (" + questionCodeKey + ") but it's not q DoubleQuestion or IntegerQuestion or PercentageQuestion, but : " + question.getClass());
                }
            }

            //test unit if the question expected a unitCategory
            //the unitCategory must be the same ad that of the bad
            if (unitCategoryQuestion != null) {
                if (unitExpected == null || unitExpected.length() == 0) {

                    //control equivalence between BAD unit.unitCat and question.unitCat
                    if (!((DoubleQuestion) question).getUnitCategory().equals(unitCategoryExpected)) {
                        badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " contains a questionCode without unit specified and the unitCategory of the question doesn't correspond to the unitCategory of the BAD : " + questionCodeKey);
                    } else {
                        badLog.addToLog(BADLog.LogType.INFO, line, "The " + type + " contains a questionCode without unit specified, but the unitCat is the same than the BAD");
                    }
                } else {

                    //test unit expected
                    if (!controlList(UnitCode.class, unitExpected)) {
                        badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " a questionCode with unit specified, but this unit was not found : " + unitExpected);
                    } else {

                        //load unit
                        unit = unitService.findByCode(new UnitCode(unitExpected));


                        //test unit
                        if (!unitCategoryQuestion.equals(unit.getCategory())) {
                            badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " contains a questionCode, but the specified unit do not " +
                                    "come from the unitCategory of the question : " + questionCodeKey + ", unitCategory of the question : " + unitCategoryQuestion + ", unitCategory of the unit : " + unit.getCategory());
                        }
                    }
                }
            }


            //replace question code by a call to the expected value
            if (question instanceof NumericQuestion) {
                // parse the content to return a double
                if (unitCategoryQuestion != null) {

                    if (unit != null) {
                        return "toDouble(question" + questionCodeKey + "Answer, getUnitByCode(UnitCode." + unit.getUnitCode().getKey() + "))";
                    } else {
                        return "toDouble(question" + questionCodeKey + "Answer, baseActivityDataUnit)";
                    }
                } else {
                    return "toDouble(question" + questionCodeKey + "Answer)";
                }
            } else if (question instanceof BooleanQuestion) {
                return "toBoolean(question" + questionCodeKey + "Answer)";
            } else if (question instanceof ValueSelectionQuestion) {
                return "getCode(question" + questionCodeKey + "Answer).getKey()";
            } else {
                //other type of question aren't supported
                badLog.addToLog(BADLog.LogType.ERROR, line, type + " : " + question.getClass() + " aren't supported (question " + questionCodeKey + ")");
                return questionCodeKey;
            }
        }
    }
*/

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
    private String controlEquation(BAD bad, String content, int line, String type) {

        //create an equation test
        String equation = content;
        String value = content;

        //1) find all question
        String patternString = "(A[A-Z]*[0-9]+)(\\[(.+?)\\])?";
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {

            String questionCodeKey = matcher.group(1);

            //a) find question by code
            //control if the questionCode if a part of the questionList
            if (!controlList(QuestionCode.class, questionCodeKey)) {

                //if the questionCode aren't into the questionList, it's an error
                badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " contains a questionCode unknown : " + questionCodeKey);
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
                        unit = controlUnitCategory(((NumericQuestion) question), matcher.group(3), bad.getUnit().getCategory(), line, type);
                    }

                } else {
                    //other question type are not accepted
                    badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " contains a questionCode (" + questionCodeKey + ") but it's not q DoubleQuestion or IntegerQuestion or PercentageQuestion, but : " + question.getClass());
                }

                //d) replace code
                if (unitCategoryQuestion != null) {
                    if (unit != null) {
                        value = value.replaceAll(convertToRegex(matcher.group()), "toDouble(question" + questionCodeKey + "Answer, getUnitByCode(UnitCode." + unit.getUnitCode().getKey() + "))");
                    } else {
                        value = value.replaceAll(convertToRegex(matcher.group()), "toDouble(question" + questionCodeKey + "Answer, baseActivityDataUnit)");
                    }
                } else {
                    value = value.replaceAll(convertToRegex(matcher.group()), "toDouble(question" + questionCodeKey + "Answer)");
                }

                // replace into equation
                equation = equation.replaceAll(convertToRegex(matcher.group()), "1");
            }
        }
        // 2) control equation with replace elements
        try {
            evaluateFormula(equation);
        } catch (Exception e) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " cannot be convert to equation: " + equation + " (" + content + ") =>" + e.getMessage());
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
     * @param line                the number of the line controlled
     * @param type                the field controlled
     * @return a unit if the equivalence is true and the equation need a unit to convert the answer. null if not
     * Complete also the log for error and / or info
     */
    private Unit controlUnitCategory(NumericQuestion question, String unitExpected, UnitCategory
            unitCategoryDefault, int line, String type) {

        //if the question doesn't have unitCategory, control useless
        if (question.getUnitCategory() != null) {

            //if there is not unit expected
            if (unitExpected == null || unitExpected.length() == 0) {

                //... continue with the unit cagtegory default
                if (unitCategoryDefault != null) {

                    //control equivalence between BAD unit.unitCat and question.unitCat
                    if (!question.getUnitCategory().equals(unitCategoryDefault)) {
                        //categories != => error !
                        badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " contains a questionCode without unit specified and the unitCategory of the question doesn't correspond to the unitCategory of the BAD : " + question.getCode().getKey());
                    } else {
                        //ok => info
                        badLog.addToLog(BADLog.LogType.INFO, line, "The " + type + " contains a questionCode without unit specified, but the unitCat is the same than the BAD");
                    }
                } else {
                    // if there is not unit expected or unitCategory default, this is an error
                    badLog.addToLog(BADLog.LogType.INFO, line, "The " + type + " contains a questionCode without unit specified");
                }
                //there is a unit expected => control it
            } else {

                //test unit expected
                if (!controlList(UnitCode.class, unitExpected)) {
                    badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " a questionCode with unit specified, but this unit was not found : " + unitExpected);
                } else {

                    //load unit
                    Unit unit = unitService.findByCode(new UnitCode(unitExpected));

                    //test unit
                    if (!question.getUnitCategory().equals(unit.getCategory())) {
                        badLog.addToLog(BADLog.LogType.ERROR, line, "The " + type + " contains a questionCode, but the specified unit do not " +
                                "come from the unitCategory of the question : " + question.getCode().getKey() + ", unitCategory of the question : " + question.getUnitCategory() + ", unitCategory of the unit : " + unit.getCategory());
                    } else {
                        return unit;
                    }
                }
            }
        }
        return null;
    }
}
