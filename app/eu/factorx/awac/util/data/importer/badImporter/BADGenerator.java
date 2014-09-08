package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import play.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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


    public void generateBAD(BAD bad) {

        //create template
        BADTemplate badTemplate = new BADTemplate(TemplateName.BAD, "BaseActivityData" + bad.getBaseActivityDataCode() + ".java");

        //add parameters
        //start to build the map of repetition to load question
        for (String question : bad.getListQuestions()) {
            completHashMap(question);
        }

        //TEMP print result
        //TODO erreur dans les listes : les questions doivent être récupéré dans leur parent direct et pas dans la répéition
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

        //TODO ...


        badTemplate.generate();
    }

    private void buildListRepetition(RepeatableElement repeatableElement, List<RepeatableElement> listRepeatableElements) {
        if (repeatableElement == null) {
            return;
        }
        listRepeatableElements.add(repeatableElement);
        if (repeatableElement.getChild() != null) {
            buildListRepetition(repeatableElement.getChild(), listRepeatableElements);
        }
    }


    private void completHashMap(String questionCode) {

        //load the question
        Question question = questionService.findByCode(new QuestionCode(questionCode));

        //there is a loop bottom the question ?
        //if (foundRepetition(question.getQuestionSet())) {

            //try to add question
            if (!addQuestion(question.getQuestionSet(), question)) {

                if (repeatableElementRoot == null) {
                    //build the structure
                    buildStructure(question.getQuestionSet(), question);
                } else {
                    //TODO try to add the repetition into the existent structure

                    //TODO LOGGER TEMP
                    Logger.info("CANNOT FOUND REPETITION ELEMEBNT FOR "+questionCode);
                }
            }
       // } else {
       //     listQuestionWithoutRepetition.add(question);
        //}

    }

    /**
     * Try to add a question into the existing structure
     * return true if the question was addeds
     */
    private boolean addQuestion(QuestionSet questionSet, Question questionToAdd) {


        //test if this QuestionSet is already know
        if (getRepeatableElement(questionSet) != null) {
            getRepeatableElement(questionSet).addQuestion(questionToAdd.getCode().getKey());
            return true;
        }

        //continue
        if (questionSet.getParent() != null) {
            return addQuestion(questionSet.getParent(), questionToAdd);
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
        if (repeatableElement.getMainQuestionSet().equals(questionSet.getCode().getKey())) {
            return repeatableElement;
        }
        if (repeatableElement.getChild() != null) {
            return getRepeatableElement(questionSet, repeatableElement.getChild());
        }
        return null;
    }

    /**
     * return true if the mainQuestionSet or one of them parent are a repetition
     */
    private boolean foundRepetition(QuestionSet questionSet) {
        if (questionSet.getRepetitionAllowed()) {
            return true;
        }
        if (questionSet.getParent() != null) {
            return foundRepetition(questionSet.getParent());
        }
        return false;
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

        RepeatableElement repeatableElement = new RepeatableElement(questionSet.getCode().getKey());
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

    /**
     * This class store a structure of mainQuestionSet.
     * The main QuestionSet is a repetition mainQuestionSet, and the Map contains mainQuestionSet children of the mainQuestionSet
     */
    public static class RepeatableElement {

        private String mainQuestionSet;

        private boolean isRepeteable;

        private List<String> questionList = new ArrayList<>();

        private RepeatableElement child;

        private RepeatableElement(String mainQuestionSet) {
            this.mainQuestionSet = mainQuestionSet;
        }

        public String getMainQuestionSet() {
            return mainQuestionSet;
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


}
