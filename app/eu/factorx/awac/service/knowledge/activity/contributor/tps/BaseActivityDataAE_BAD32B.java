package eu.factorx.awac.service.knowledge.activity.contributor.tps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;

/**
* AUTO-GENERATED by BADImportator at 09/09/2014 04:22
* Name of the BAD : Site: transport amont externe estimé camion
 */
public class BaseActivityDataAE_BAD32B extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = l )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5126);


        //2. build BAD
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersA244 = questionSetAnswers.get(QuestionCode.A244);
                //2.2 control if the list if different than null
                if (questionSetAnswersA244 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA244) {
             
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.A250)) {
             
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer3 : getChildrenQuestionSetAnswers(questionSetAnswer2,QuestionCode.A252)) {
             
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer4 : getChildrenQuestionSetAnswers(questionSetAnswer3,QuestionCode.A266)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode4 = byQuestionCode(questionSetAnswer4.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA267Answer = answersByCode4.get(QuestionCode.A267);
                                    QuestionAnswer questionA268Answer = answersByCode4.get(QuestionCode.A268);
                
                    //control them
                    if (                            questionA267Answer == null  ||
                                                                                questionA268Answer == null                                                 ) {
                       continue;
                    }
                    
                        if(getCode(questionA268Answer).getKey().equals("2")){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD32B);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_12);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_162);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(0,39*2500*toDouble(questionA267Answer, getUnitByCode(UnitCode.U5135))/11,4/0,4426*24,98/100);
            res.add(baseActivityData);

                        }
            
                         }
                         }
                         }
                         }
                 return res;
    }

}