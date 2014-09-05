package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.type.DoubleAnswerValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.type.*;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.models.knowledge.UnitConversionFormula;
import eu.factorx.awac.service.QuestionService;
import eu.factorx.awac.service.UnitService;
import eu.factorx.awac.util.MyrmexException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
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
        bad.setBaseActivityDataCode(badKey);
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
            try {
                if (controlQuestionType(content, StringQuestion.class)) {
                    badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a SpecificPurpose and it's a Stringquestion");

                    bad.setSpecificPurpose("toString(question" + content + "Answer)");
                    bad.addQuestion(content);

                } else {
                    badLog.addToLog(BADLog.LogType.ERROR, line, "SpecificPurpose : it's a question but not a StringQuestion : SpecificPurpose will be null");
                }
            } catch (MyrmexException e) {
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
            //TODO control the type of the question
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activitySubCategory and it's a question");


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
            //TODO control the type of the question
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activityType and it's a question");

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
            //TODO control the type of the question
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activitySource and it's a question");

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
            try {
                if (controlQuestionType(content, BooleanQuestion.class)) {

                    //add to bad
                    bad.setActivityOwnership("toBoolean(question" + content + "Answer)");
                    bad.addQuestion(content);

                } else {
                    badLog.addToLog(BADLog.LogType.ERROR, line, "ActivityOwnerShip  : this is a questionCode but this question is not BooleanQuestion : " + content);
                }
            } catch (MyrmexException e) {
                badLog.addToLog(BADLog.LogType.ERROR, line, "ActivityOwnerShip is not null but it's not a boolean or a question : " + content);
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

        //create an equation test
        String equation = content;
        String value = content;

        //first : find all question
        String patternString = "(A[A-Z0-9]+)(\\[(.+?)\\])?";
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(equation);

        while (matcher.find()) {

            //TODO test unit

            String questionCodeKey = matcher.group(1);

            //test question
            if (!controlList(QuestionCode.class, questionCodeKey)) {
                badLog.addToLog(BADLog.LogType.ERROR, line, "The value contains a questionCode unknown : " + questionCodeKey);
            } else {

                //load question
                Question question = questionService.findByCode(new QuestionCode(questionCodeKey));
                UnitCategory unitCategoryQuestion = null;
                Unit unit = null;

                //find unit
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
                    badLog.addToLog(BADLog.LogType.ERROR, line, "The value contains a questionCode (" + questionCodeKey + ") but it's not q DoubleQuestion or IntegerQuestion, but : " + question.getClass());
                }

                //test unit
                if (unitCategoryQuestion != null) {
                    if (matcher.group(2) == null || matcher.group(2).length() == 0) {
                        badLog.addToLog(BADLog.LogType.WARNING, line, "The value contains a questionCode without unit specified : " + questionCodeKey);
                    } else {

                        String unitExpected = matcher.group(3);

                        //test unit expected
                        if (!controlList(UnitCode.class, unitExpected)) {
                            badLog.addToLog(BADLog.LogType.ERROR, line, "The value contains a questionCode with unit specified, but this unit was not found : " + matcher.group() + ", " + unitExpected);
                        } else {

                            //load unit
                            unit = unitService.findByCode(new UnitCode(unitExpected));


                            //test unit
                            if (!unitCategoryQuestion.equals(unit.getCategory())) {
                                badLog.addToLog(BADLog.LogType.ERROR, line, "The value contains a questionCode, but the specified unit do not " +
                                        "come from the unitCategory of the question : " + questionCodeKey + ", unitCategory of the question : " + unitCategoryQuestion + ", unitCategory of the unit : " + unit.getCategory());
                            }
                        }
                    }
                }

                // toDouble(questionA17Answer, baseActivityDataUnit)
                if (unitCategoryQuestion != null) {

                    if (unit != null) {
                        value = value.replaceAll(patternString, "toDouble(question" + questionCodeKey + "Answer, getUnitByCode(UnitCode." + unit.getUnitCode().getKey() + "))");
                    } else {
                        value = value.replaceAll(patternString, "toDouble(question" + questionCodeKey + "Answer, baseActivityDataUnit)");
                    }
                } else {
                    value = value.replaceAll(patternString, "toDouble(question" + questionCodeKey + "Answer)");
                }

                bad.addQuestion(matcher.group(1));
                //replace into equation
                equation = equation.replaceAll(patternString, "1");
            }
        }

        //control euqation
        if (!evaluateFormula(equation)) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "The value cannot be convert to equation: " + content);
        }

        bad.setValue(value);
    }

    /**
     * TODO control equation
     */
    public void controlCondition(String content, int line, BAD bad) {
        //TODO implement question control

        bad.setCondition(content);
    }


    /* ********************************
    ***************** PRIVATE ***********
    ********************************** */


    private boolean evaluateFormula(String formula) {

        //convert , to .
        formula = formula.replaceAll(",", ".");

        Double result = null;
        try {
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(formula);
            result = expression.getValue(Double.class);
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

    private <T extends Question> boolean controlQuestionType(String questionCode, Class<T> questionClass) throws MyrmexException {

        //control code
        if (!controlList(QuestionCode.class, questionCode)) {
            throw new MyrmexException("This is not a questionCode");
        }

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


}
