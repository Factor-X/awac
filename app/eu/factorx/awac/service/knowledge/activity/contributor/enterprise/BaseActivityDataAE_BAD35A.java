package eu.factorx.awac.service.knowledge.activity.contributor.enterprise;

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
* AUTO-GENERATED by BADImportator at 22/10/2014 08:13
* Name of the BAD : Site: utilisation aval - combustion directe
 */
@Component
public class BaseActivityDataAE_BAD35A extends ActivityResultContributor {

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
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA245Answer = answersByCode1.get(QuestionCode.A245);
                                    QuestionAnswer questionA246Answer = answersByCode1.get(QuestionCode.A246);
                
                    //control them
                    if (                            questionA245Answer == null  ||
                                                                                questionA246Answer == null                                                 ) {
                       continue;
                    }
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.A291)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode2 = byQuestionCode(questionSetAnswer2.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA293Answer = answersByCode2.get(QuestionCode.A293);
                                    QuestionAnswer questionA294Answer = answersByCode2.get(QuestionCode.A294);
                
                    //control them
                    if (                            questionA293Answer == null  ||
                                                                                questionA294Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD35A);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(toString(questionA245Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_12);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_1);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_162);

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA246Answer)*toDouble(questionA293Answer)*toDouble(questionA294Answer, getUnitByCode(UnitCode.U5126)));
            res.add(baseActivityData);

            
                         }
                         }
                 return res;
    }

}