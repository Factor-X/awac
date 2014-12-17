package eu.factorx.awac.service.knowledge.activity.contributor.littleemitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.conversion.ConversionCriterion;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;
import org.springframework.stereotype.Component;

/**
* AUTO-GENERATED by BADImportator at 17/12/2014 10:10
* Name of the BAD : mobilité DDT aggrégé
 */
@Component
public class BaseActivityDataAPE_BAD8G extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = passagers.km )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5328);


        //2. build BAD

                //load question without repetition
                    List<QuestionSetAnswer> questionSetAnswersAP91 = questionSetAnswers.get(QuestionCode.AP91);
                    List<QuestionSetAnswer> questionSetAnswersAP93 = questionSetAnswers.get(QuestionCode.AP93);
                    List<QuestionSetAnswer> questionSetAnswersAP78 = questionSetAnswers.get(QuestionCode.AP78);
        
        //... and control
        if (
                    questionSetAnswersAP91 == null || questionSetAnswersAP91.size()==0  ||
                                questionSetAnswersAP93 == null || questionSetAnswersAP93.size()==0  ||
                                questionSetAnswersAP78 == null || questionSetAnswersAP78.size()==0         ) {
        return res;
        }

        //load question
                        QuestionAnswer questionAP92Answer = byQuestionCode(questionSetAnswersAP91.get(0).getQuestionAnswers()).get(QuestionCode.AP92);
                                QuestionAnswer questionAP99Answer = byQuestionCode(questionSetAnswersAP93.get(0).getQuestionAnswers()).get(QuestionCode.AP99);
                                QuestionAnswer questionAP80Answer = byQuestionCode(questionSetAnswersAP78.get(0).getQuestionAnswers()).get(QuestionCode.AP80);
                
        //control
        if(false
                             || questionAP92Answer==null
                              || questionAP99Answer==null
                              || questionAP80Answer==null
                     ){
            return res;
        }

        



        //and control

        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersAP2 = questionSetAnswers.get(QuestionCode.AP2);
                //2.2 control if the list if different than null
                if (questionSetAnswersAP2 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAP2) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAP7Answer = answersByCode1.get(QuestionCode.AP7);
                
                    //control them
                    if (                            questionAP7Answer == null                                                 ) {
                       continue;
                    }
                    
                        if(getCode(questionAP80Answer).getKey().equals("2")){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.APE_BAD8G);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_8);
            baseActivityData.setActivityType(ActivityTypeCode.AT_13);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_164);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAP7Answer)*toDouble(questionAP92Answer, getUnitByCode(UnitCode.U5106))*2*220*toDouble(questionAP99Answer));
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}