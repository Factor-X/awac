package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.type.BooleanQuestion;
import eu.factorx.awac.service.QuestionService;
import eu.factorx.awac.util.MyrmexException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * Created by florian on 4/09/14.
 */
@Component
public class BADControlElement {


    @Autowired
    private QuestionService questionService;


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
    public String controlBADKey(String cellContent, int line) throws MyrmexException {

        String badKey = cellContent;
        if (badKey == null || badKey.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no badKey : " + cellContent);
            throw new MyrmexException("");
        }

        if (!controlList(BaseActivityDataCode.class, badKey)) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "This is not a badKey : " + cellContent);
            throw new MyrmexException("");
        }
        return badKey;
    }

    /**
     * control the name of the BAD
     * The name is only a string
     * cannot be null
     */
    public String controlName(String content, int line) throws MyrmexException {
        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no name: " + content);
            throw new MyrmexException("");
        }
        return content;
    }

    /**
     * Rank cannot be null and can be a number
     * return null otherwise
     */
    public Integer controlRank(String content, int line) throws MyrmexException {

        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.WARNING, line, "There rank is null");
            throw new MyrmexException("");
        }
        try {
            return Integer.parseInt(content);
        } catch (NumberFormatException e) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "The rank is not null but it's not a valid number  : " + content);
        }
        throw new MyrmexException("");
    }

    /**
     * Can be an activityCode or an answer or can be null
     */
    public String controlSpecificPurpose(String content, int line) throws MyrmexException {
        if (content != null && content.length() > 0) {

            //test if the specific purpose is a code
            if (controlList(ActivityCategoryCode.class, content)) {
                badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a SpecificPurpose and it's an activityCode");
            } else if (controlList(QuestionCode.class, content)) {
                badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a SpecificPurpose and it's a question");
            } else {
                badLog.addToLog(BADLog.LogType.WARNING, line, "There is a SpecificPurpose but it's not an activityCode or an answer.");
            }
            return content;
        } else {
            badLog.addToLog(BADLog.LogType.WARNING, line, "The SpecificPurpose is null");
            return null;
        }
    }

    /**
     * Cannot be nul, always a activityCategory
     */
    public String controlActivityCategory(String content, int line) throws MyrmexException {

        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no activityCategory : " + content);
            throw new MyrmexException("");
        }

        //save all data into one object
        if (!controlList(ActivityCategoryCode.class, content)) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "This activityCategory was not found : " + content);
            throw new MyrmexException("");
        }
        return content;
    }

    /**
     * Cannot be nul, can be  a activitySubCategory or a question using a list composed to  activitySubCategory element or
     * //TODO more complex
     */
    public String controlActivitySubCategory(String content, int line) throws MyrmexException {

        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no activitySubCategory : " + content);
            throw new MyrmexException("");
        }
        //test if the activitySubCategory is a code
        if (controlList(ActivitySubCategoryCode.class, content)) {
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activitySubCategory and it's an ActivitySubCategoryCode");
        } else if (controlList(QuestionCode.class, content)) {
            //TODO control the type of the question
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activitySubCategory and it's a question");
        } else {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is a activitySubCategory but it's not an ActivitySubCategoryCode or an answer." + content);
        }
        return content;
    }

    /**
     * Cannot be nul, can be  a activitySubCategory or a question using a list composed to  activitySubCategory element or
     * //TODO more complex
     */
    public String controlActivityType(String content, int line) throws MyrmexException {

        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no activityType : " + content);
            throw new MyrmexException("");
        }

        //test if the activityType is a code
        if (controlList(ActivityTypeCode.class, content)) {
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activityType and it's an ActivitySubCategoryCode");
        } else if (controlList(QuestionCode.class, content)) {
            //TODO control the type of the question
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activityType and it's a question");
        } else {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is a activityType but it's not an activityType or an answer." + content);
        }
        return content;
    }

    /**
     * Cannot be nul, can be  a activitySubCategory or a question using a list composed to  activitySubCategory element or
     * //TODO more complex
     */
    public String controlActivitySource(String content, int line) throws MyrmexException {


        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no activitySource : " + content);
            throw new MyrmexException("");
        }

        //test if the activitySubCategory is a code
        if (controlList(ActivitySourceCode.class, content)) {
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activitySource and it's an ActivitySubCategoryCode");
        } else if (controlList(QuestionCode.class, content)) {
            //TODO control the type of the question
            badLog.addToLog(BADLog.LogType.DEBUG, line, "There is a activitySource and it's a question");
        } else {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is a activitySource but it's not an activityType or an answer." + content);
        }
        return content;
    }

    /**
     * can be null. Boolean expected or BooleanQuestion type
     */
    public String controlActivityOwnerShip(String content, int line) throws MyrmexException {

        boolean activityOwnerShipValid = false;
        if (content == null) {
            badLog.addToLog(BADLog.LogType.WARNING, line, "ActivityOwnerShip is null");
        } else {

            //try to convert to boolean
            if (controlBoolean(content) != null) {
                return controlBoolean(content);
            }

            //try to convert to BooleanQuestion
            try {
                if (controlQuestionType(content, BooleanQuestion.class)) {
                    return content;
                } else {
                    badLog.addToLog(BADLog.LogType.ERROR, line, "ActivityOwnerShip  : this is a questionCode but this question is not BooleanQuestion : " + content);
                }
            } catch (MyrmexException e) {
                badLog.addToLog(BADLog.LogType.ERROR, line, "ActivityOwnerShip is not null but it's not a boolean or a question : " + content);
            }
        }
        return null;
    }

    /**
     * cannot be null. Except a unitCode
     */
    public String controlUnit(String content, int line) throws MyrmexException {
        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no unit : " + content);
            throw new MyrmexException("");
        }
        //test if the activityType is a code
        if (!controlList(UnitCode.class, content)) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "This is not a unit code : " + content);
            throw new MyrmexException("");
        }
        return content;
    }

    /**
     * Cannot be null
     * TODO control equation
     */
    public String controlValue(String content, int line) throws MyrmexException {
        if (content == null || content.length() == 0) {
            badLog.addToLog(BADLog.LogType.ERROR, line, "There is no value : " + content + ". The bad was not generated");
            throw new MyrmexException("");
        }
        //TODO implement question control
        return content;
    }

    /**
     * TODO control equation
     */
    public String controlCondition(String content, int line) throws MyrmexException {
        //TODO implement question control
        return content;
    }


    public boolean controlList(Class classToTest, String code) {
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
