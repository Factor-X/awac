package eu.factorx.awac.service.knowledge.activity.contributor.municipality;

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
* AUTO-GENERATED by BADImportator at 18/09/2014 05:43
* Name of the BAD : achats matière
 */
@Component
public class BaseActivityDataAC_BAD14H extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = t )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5135);


        //2. build BAD

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersAC116 = questionSetAnswers.get(QuestionCode.AC116);
                //2.2 control if the list if different than null
                if (questionSetAnswersAC116 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAC116) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC117Answer = answersByCode1.get(QuestionCode.AC117);
                                    QuestionAnswer questionAC118Answer = answersByCode1.get(QuestionCode.AC118);
                                    QuestionAnswer questionAC122Answer = answersByCode1.get(QuestionCode.AC122);
                                    QuestionAnswer questionAC127Answer = answersByCode1.get(QuestionCode.AC127);
                                    QuestionAnswer questionAC128Answer = answersByCode1.get(QuestionCode.AC128);
                
                    //control them
                    if (                            questionAC117Answer == null  ||
                                                                                questionAC118Answer == null  ||
                                                                                questionAC122Answer == null  ||
                                                                                questionAC127Answer == null  ||
                                                                                questionAC128Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AC_BAD14H);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionAC117Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_8);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
            baseActivityData.setActivityType(toActivityTypeCode(questionAC118Answer));

            baseActivityData.setActivitySource(convertCode(toActivitySourceCode(questionAC122Answer), ConversionCriterion.RECYCLAGE));

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAC127Answer)*toDouble(questionAC128Answer, getUnitByCode(UnitCode.U5135)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}