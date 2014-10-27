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
* AUTO-GENERATED by BADImportator at 27/10/2014 07:25
* Name of the BAD : Site: transport amont externe avion moyen
 */
@Component
public class BaseActivityDataAE_BAD17H extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = tonnes.km )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5329);


        //2. build BAD

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersA142 = questionSetAnswers.get(QuestionCode.A142);
                //2.2 control if the list if different than null
                if (questionSetAnswersA142 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA142) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA145Answer = answersByCode1.get(QuestionCode.A145);
                                    QuestionAnswer questionA146Answer = answersByCode1.get(QuestionCode.A146);
                                    QuestionAnswer questionA154Answer = answersByCode1.get(QuestionCode.A154);
                                    QuestionAnswer questionA147Answer = answersByCode1.get(QuestionCode.A147);
                                    QuestionAnswer questionA148Answer = answersByCode1.get(QuestionCode.A148);
                                    QuestionAnswer questionA149Answer = answersByCode1.get(QuestionCode.A149);
                                    QuestionAnswer questionA150Answer = answersByCode1.get(QuestionCode.A150);
                                    QuestionAnswer questionA151Answer = answersByCode1.get(QuestionCode.A151);
                                    QuestionAnswer questionA152Answer = answersByCode1.get(QuestionCode.A152);
                                    QuestionAnswer questionA153Answer = answersByCode1.get(QuestionCode.A153);
                                    QuestionAnswer questionA155Answer = answersByCode1.get(QuestionCode.A155);
                
                    //control them
                    if (                            questionA145Answer == null  ||
                                                                                questionA146Answer == null  ||
                                                                                questionA154Answer == null  ||
                                                                                questionA147Answer == null  ||
                                                                                questionA148Answer == null  ||
                                                                                questionA149Answer == null  ||
                                                                                questionA150Answer == null  ||
                                                                                questionA151Answer == null  ||
                                                                                questionA152Answer == null  ||
                                                                                questionA153Answer == null  ||
                                                                                questionA155Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD17H);
                            baseActivityData.setAlternativeGroup("ALT_E_5_AMONT");
                        baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_6);
            baseActivityData.setActivityType(ActivityTypeCode.AT_29);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_177);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA145Answer, getUnitByCode(UnitCode.U5135))*toDouble(questionA146Answer, getUnitByCode(UnitCode.U5106))*toDouble(questionA154Answer)+0*(toDouble(questionA147Answer)+toDouble(questionA148Answer)+toDouble(questionA149Answer)+toDouble(questionA150Answer)+toDouble(questionA151Answer)+toDouble(questionA152Answer)+toDouble(questionA153Answer)+toDouble(questionA154Answer)+toDouble(questionA155Answer)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}