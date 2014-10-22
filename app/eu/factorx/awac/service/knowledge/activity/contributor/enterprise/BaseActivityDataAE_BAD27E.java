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
* AUTO-GENERATED by BADImportator at 22/10/2014 11:56
* Name of the BAD : Site: achats matière
 */
@Component
public class BaseActivityDataAE_BAD27E extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA209 = questionSetAnswers.get(QuestionCode.A209);
                //2.2 control if the list if different than null
                if (questionSetAnswersA209 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA209) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA210Answer = answersByCode1.get(QuestionCode.A210);
                                    QuestionAnswer questionA211Answer = answersByCode1.get(QuestionCode.A211);
                                    QuestionAnswer questionA214Answer = answersByCode1.get(QuestionCode.A214);
                                    QuestionAnswer questionA221Answer = answersByCode1.get(QuestionCode.A221);
                                    QuestionAnswer questionA220Answer = answersByCode1.get(QuestionCode.A220);
                
                    //control them
                    if (                            questionA210Answer == null  ||
                                                                                questionA211Answer == null  ||
                                                                                questionA214Answer == null  ||
                                                                                questionA221Answer == null  ||
                                                                                questionA220Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD27E);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(toString(questionA210Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_8);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
            baseActivityData.setActivityType(toActivityTypeCode(questionA211Answer));

            baseActivityData.setActivitySource(toActivitySourceCode(questionA214Answer));

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA221Answer, getUnitByCode(UnitCode.U5135))*(1-toDouble(questionA220Answer)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}