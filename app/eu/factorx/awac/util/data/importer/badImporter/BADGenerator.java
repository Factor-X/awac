package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by florian on 4/09/14.
 */
@Component
@Scope("prototype")
public class BADGenerator {


    private RepeatableElement repeatableElementRoot = null;
    //private List<Question> listQuestionWithoutRepetition = new ArrayList<>();


    @Autowired
    private QuestionService questionService;

    @Autowired
    private CodeLabelService codeLabelService;

    private List<QuestionWithoutRepetition> questionWithoutRepetitionList = new ArrayList<>();


    public void generateBAD(BAD bad, BADLog.LogLine logLine) {

        //create template
        BADTemplate badTemplate = new BADTemplate(TemplateName.BAD, "BaseActivityData" + bad.getBaseActivityDataCode() + ".java");

        //add parameters
        //start to build the map of repetition to load question
        for (String question : bad.getListQuestions()) {
            completHashMap(question, logLine, bad.getLine());
        }

        //TEMP print result
        /*
        if (bad.getListQuestions().size() > 0) {
            Logger.info("getListQuestions:" + bad.getListQuestions().toString());

            if (repeatableElementRoot != null) {
                Logger.info("repeatableElementRoot:" + repeatableElementRoot.toString());
            }
            //Logger.info("listQuestionWithoutRepetition:" + listQuestionWithoutRepetition.toString());
        }
*/
        //baseActivityDate key and name
        badTemplate.addParameter("BAD-KEY", bad.getBaseActivityDataCode());
        badTemplate.addParameter("BAD-NAME", bad.getName());

        //current date
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyy hh:mm");
        badTemplate.addParameter("DATE", formatter.format(new Date()));

        //unit
        badTemplate.addParameter("UNIT-SYMBOL", bad.getUnit().getSymbol());
        badTemplate.addParameter("UNIT-KEY", bad.getUnit().getUnitCode().getKey());

        //rank
        badTemplate.addParameter("RANK", bad.getRank() + "");

        //specific purpose
        badTemplate.addParameter("SPECIFIC-PURPOSE", bad.getSpecificPurpose());

        //activity cat
        badTemplate.addParameter("ACTIVITY-CATEGORY", bad.getActivityCategoryCode());

        //activity sub category
        badTemplate.addParameter("ACTIVITY-SUB-CATEGORY", bad.getActivitySubCategory());

        //activity type
        badTemplate.addParameter("ACTIVITY-TYPE", bad.getActivityType());

        //activity source
        badTemplate.addParameter("ACTIVITY-SOURCE", bad.getActivitySource());

        //activity ownership
        badTemplate.addParameter("ACTIVITY-OWNERSHIP", bad.getActivityOwnership());

        //value
        badTemplate.addParameter("VALUE", bad.getValue());

        //build the repetition Map
        List<RepeatableElement> repetition = new ArrayList<>();
        buildListRepetition(repeatableElementRoot, repetition);
        //start with parent and continue...

        //repetition
        badTemplate.addParameter("repetitions", repetition);

        //question without repetition
        badTemplate.addParameter("question-without-repetition", questionWithoutRepetitionList);

        //condition
        if (bad.getCondition() != null && bad.getCondition().length() > 0) {
            badTemplate.addParameter("HAS_CONDITION", true);
            badTemplate.addParameter("CONDITION", bad.getCondition());
        } else {
            badTemplate.addParameter("HAS_CONDITION", false);
        }

        //TODO ...


        badTemplate.generate(TemplateName.BAD.getTargetPath());
    }

    private void buildListRepetition(RepeatableElement repeatableElement, List<RepeatableElement> listRepeatableElements) {
        if (repeatableElement == null) {
            return;
        }

        //ignore questionSet if there is first and not repeatable or doesn't have liked question
        if (listRepeatableElements.size() > 0 || repeatableElement.getMainQuestionSet().getRepetitionAllowed() || repeatableElement.getQuestionList().size() > 0) {
            listRepeatableElements.add(repeatableElement);
        }
        if (repeatableElement.getChild() != null) {
            buildListRepetition(repeatableElement.getChild(), listRepeatableElements);
        }
    }


    private void completHashMap(String questionCode, BADLog.LogLine logLine, int line) {

        //load the question
        Question question = questionService.findByCode(new QuestionCode(questionCode));

        //there is a loop bottom the question ?
        //if (foundRepetition(question.getQuestionSet())) {

        //try to add question
        if (!addQuestion(question)) {

            if (repeatableElementRoot == null) {
                //build the structure
                buildStructure(question.getQuestionSet(), question);
            } else {

                // get last child
                RepeatableElement repeatableElementLastChild = getLastChild(repeatableElementRoot);

                //try to match questionCode.questionSet with this element
                QuestionSet questionSetCompatible = getCompatibleQuestionSet(repeatableElementLastChild, question.getQuestionSet(), null);
                if (questionSetCompatible == null) {

                    //try if the question has a repetition into his parents
                    if (haveRepetitionParent(question.getQuestionSet())) {
                        logLine.addError("error : " + question.getQuestionSet().getCode().getKey() + " cannot be insert into the questionSet structure (root : " + repeatableElementRoot.getMainQuestionSetString() + ",lastChild : " + repeatableElementLastChild.getMainQuestionSetString() + ", question : " + questionCode + ")");
                    }

                    //add question to questionWithoutRepetitionList
                    boolean founded = false;
                    for (QuestionWithoutRepetition questionWithoutRepetition : questionWithoutRepetitionList) {
                        if (questionWithoutRepetition.getQuestionSet().equals(question.getQuestionSet())) {
                            questionWithoutRepetition.addQuestion(question);
                            founded = true;
                            break;
                        }
                    }
                    if (!founded) {
                        questionWithoutRepetitionList.add(new QuestionWithoutRepetition(question.getQuestionSet(), question));
                    }

                } else {
                    //add
                    addToStructure(question.getQuestionSet(), question, repeatableElementLastChild);
                }
            }
        }
    }

    private QuestionSet getCompatibleQuestionSet(RepeatableElement repeatableElement, QuestionSet questionSet, QuestionSet lastQuestionSet) {
        if (repeatableElement.getMainQuestionSet().equals(questionSet)) {
            return lastQuestionSet;
        } else if (questionSet.getParent() != null) {
            return getCompatibleQuestionSet(repeatableElement, questionSet.getParent(), questionSet);
        }
        return null;
    }

    /**
     * return the last child
     *
     * @param repeatableElement
     * @return
     */
    private RepeatableElement getLastChild(RepeatableElement repeatableElement) {
        if (repeatableElement.getChild() != null) {
            return getLastChild(repeatableElement.getChild());
        } else {
            return repeatableElement;
        }
    }

    /**
     * Try to add a question into the existing structure
     * return true if the question was addeds
     */
    private boolean addQuestion(Question questionToAdd) {

        //test if this QuestionSet is already know
        if (getRepeatableElement(questionToAdd.getQuestionSet()) != null) {
            getRepeatableElement(questionToAdd.getQuestionSet()).addQuestion(questionToAdd.getCode().getKey());
            return true;
        }
        return false;
    }

    /**
     * return a repeatableElement contain the asked question
     */
    private RepeatableElement getRepeatableElement(QuestionSet questionSet) {
        return getRepeatableElement(questionSet, null);
    }

    /**
     * return a repeatableElement contain the asked question
     */
    private RepeatableElement getRepeatableElement(QuestionSet questionSet, RepeatableElement repeatableElement) {
        if (repeatableElement == null) {
            if (repeatableElementRoot == null) {
                return null;
            }
            repeatableElement = repeatableElementRoot;
        }
        if (repeatableElement.getMainQuestionSet().equals(questionSet)) {
            return repeatableElement;
        }
        if (repeatableElement.getChild() != null) {
            return getRepeatableElement(questionSet, repeatableElement.getChild());
        }
        return null;
    }


    private void addToStructure(QuestionSet questionSetToAdd, Question questionToAdd, RepeatableElement repetitionReference) {
        addToStructure(questionSetToAdd, questionToAdd, repetitionReference, null);
    }

    private void addToStructure(QuestionSet questionSetToAdd, Question questionToAdd, RepeatableElement repetitionReference, RepeatableElement lastRepetition) {

        if (questionSetToAdd.equals(repetitionReference.getMainQuestionSet())) {
            repetitionReference.setChild(lastRepetition);
        } else {

            RepeatableElement repeatableElement = new RepeatableElement(questionSetToAdd);
            repeatableElement.setRepeteable(questionSetToAdd.getRepetitionAllowed());

            if (questionToAdd.getQuestionSet().equals(questionSetToAdd)) {
                repeatableElement.addQuestion(questionToAdd.getCode().getKey());
            }

            if (lastRepetition != null) {
                repeatableElement.setChild(lastRepetition);
            }

            addToStructure(questionSetToAdd.getParent(), questionToAdd, repetitionReference, repeatableElement);
        }
    }


    /**
     * build structure - initialization
     */
    private void buildStructure(QuestionSet questionSet, Question questionToAdd) {
        buildStructure(questionSet, questionToAdd, null);
    }

    /**
     * build structure from 0
     */
    private void buildStructure(QuestionSet questionSet, Question questionToAdd, RepeatableElement lastRepetition) {

        RepeatableElement repeatableElement = new RepeatableElement(questionSet);
        repeatableElement.setRepeteable(questionSet.getRepetitionAllowed());

        if (questionToAdd != null) {
            repeatableElement.addQuestion(questionToAdd.getCode().getKey());
            questionToAdd = null;
        }
        if (lastRepetition != null) {
            repeatableElement.setChild(lastRepetition);
        }
        lastRepetition = repeatableElement;

        //

        if (questionSet.getParent() != null) {
            buildStructure(questionSet.getParent(), questionToAdd, lastRepetition);
        } else {
            repeatableElementRoot = lastRepetition;
        }

    }

    public boolean haveRepetitionParent(QuestionSet questionSet) {
        if (questionSet.getRepetitionAllowed()) {
            return true;
        } else if (questionSet.getParent() != null) {
            return haveRepetitionParent(questionSet.getParent());
        }
        return false;
    }

    /**
     * This class store a structure of mainQuestionSet.
     * The main QuestionSet is a repetition mainQuestionSet, and the Map contains mainQuestionSet children of the mainQuestionSet
     */
    public class RepeatableElement {

        private QuestionSet mainQuestionSet;

        private String mainQuestionSetString;

        private String mainQuestionSetDescription;

        private boolean isRepeteable;

        private List<String> questionList = new ArrayList<>();

        private RepeatableElement child;

        public QuestionSet getMainQuestionSet() {
            return mainQuestionSet;
        }

        public RepeatableElement(QuestionSet mainQuestionSet) {
            this.setMainQuestionSet(mainQuestionSet);
        }

        public void setMainQuestionSet(QuestionSet mainQuestionSet) {
            this.mainQuestionSet = mainQuestionSet;
            this.mainQuestionSetString = mainQuestionSet.getCode().getKey();
            //TODO mainQuestionSetDescription = codeLabelService.findCodeLabelByCode(mainQuestionSet.getCode()).getLabelFr();
        }

        public String getMainQuestionSetString() {
            return mainQuestionSetString;
        }

        public RepeatableElement getChild() {
            return child;
        }

        public void setChild(RepeatableElement child) {
            this.child = child;
        }

        public boolean isRepeteable() {
            return isRepeteable;
        }

        public void setRepeteable(boolean isRepeteable) {
            this.isRepeteable = isRepeteable;
        }

        @Override
        public String toString() {

            return "RepeatableElement{" +
                    "mainQuestionSet=" + mainQuestionSet +
                    ", questionList=" + questionList +
                    ", child=" + child +
                    '}';
        }

        public void addQuestion(String questionCode) {
            questionList.add(questionCode);
        }

        public List<String> getQuestionList() {
            return questionList;
        }
    }

    public class QuestionWithoutRepetition {

        private QuestionSet questionSet;

        private List<Question> questionList;

        public QuestionWithoutRepetition(QuestionSet questionSet, Question question) {
            this.questionSet = questionSet;
            this.addQuestion(question);
        }

        public QuestionSet getQuestionSet() {
            return questionSet;
        }

        public void setQuestionSet(QuestionSet questionSet) {
            this.questionSet = questionSet;
        }

        public List<Question> getQuestionList() {
            return questionList;
        }

        public List<String> getQuestionListString() {
            List<String> result = new ArrayList<>();
            for (Question question : getQuestionList()) {
                result.add(question.getCode().getKey());
            }
            return result;
        }

        public void setQuestionList(List<Question> questionList) {
            this.questionList = questionList;
        }

        public void addQuestion(Question question) {
            if (questionList == null) {
                questionList = new ArrayList<>();
            }
            questionList.add(question);
        }

        public String getQuestionSetString() {
            return questionSet.getCode().getKey();
        }
    }


}
