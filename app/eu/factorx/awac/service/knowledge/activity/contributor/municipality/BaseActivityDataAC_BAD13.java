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
* AUTO-GENERATED by BADImportator at 19/09/2014 09:44
* Name of the BAD : mobilité DPRO avion direct
 */
@Component
public class BaseActivityDataAC_BAD13 extends ActivityResultContributor {

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

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersAC107 = questionSetAnswers.get(QuestionCode.AC107);
                //2.2 control if the list if different than null
                if (questionSetAnswersAC107 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAC107) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC108Answer = answersByCode1.get(QuestionCode.AC108);
                                    QuestionAnswer questionAC113Answer = answersByCode1.get(QuestionCode.AC113);
                                    QuestionAnswer questionAC110Answer = answersByCode1.get(QuestionCode.AC110);
                                    QuestionAnswer questionAC109Answer = answersByCode1.get(QuestionCode.AC109);
                                    QuestionAnswer questionAC111Answer = answersByCode1.get(QuestionCode.AC111);
                                    QuestionAnswer questionAC112Answer = answersByCode1.get(QuestionCode.AC112);
                
                    //control them
                    if (                            questionAC108Answer == null  ||
                                                                                questionAC113Answer == null  ||
                                                                                questionAC110Answer == null  ||
                                                                                questionAC109Answer == null  ||
                                                                                questionAC111Answer == null  ||
                                                                                questionAC112Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AC_BAD13);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionAC108Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(toActivitySubCategoryCode(questionAC113Answer));
            baseActivityData.setActivityType(toActivityTypeCode(questionAC110Answer));

            baseActivityData.setActivitySource(toActivitySourceCode(questionAC109Answer));

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAC111Answer)*toDouble(questionAC112Answer, getUnitByCode(UnitCode.U5106)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}