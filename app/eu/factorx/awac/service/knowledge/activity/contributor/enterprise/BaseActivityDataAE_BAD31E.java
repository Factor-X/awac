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
* AUTO-GENERATED by BADImportator at 27/10/2014 09:10
* Name of the BAD : Site: transport aval externe bateau
 */
@Component
public class BaseActivityDataAE_BAD31E extends ActivityResultContributor {

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
                
                    //control them
                    if (                            questionA245Answer == null                                                 ) {
                       continue;
                    }
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.A250)) {
             
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer3 : getChildrenQuestionSetAnswers(questionSetAnswer2,QuestionCode.A252)) {
             
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer4 : getChildrenQuestionSetAnswers(questionSetAnswer3,QuestionCode.A253)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode4 = byQuestionCode(questionSetAnswer4.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA254Answer = answersByCode4.get(QuestionCode.A254);
                                    QuestionAnswer questionA255Answer = answersByCode4.get(QuestionCode.A255);
                                    QuestionAnswer questionA260Answer = answersByCode4.get(QuestionCode.A260);
                                    QuestionAnswer questionA256Answer = answersByCode4.get(QuestionCode.A256);
                                    QuestionAnswer questionA257Answer = answersByCode4.get(QuestionCode.A257);
                                    QuestionAnswer questionA258Answer = answersByCode4.get(QuestionCode.A258);
                                    QuestionAnswer questionA259Answer = answersByCode4.get(QuestionCode.A259);
                                    QuestionAnswer questionA261Answer = answersByCode4.get(QuestionCode.A261);
                                    QuestionAnswer questionA262Answer = answersByCode4.get(QuestionCode.A262);
                                    QuestionAnswer questionA263Answer = answersByCode4.get(QuestionCode.A263);
                                    QuestionAnswer questionA264Answer = answersByCode4.get(QuestionCode.A264);
                
                    //control them
                    if (                            questionA254Answer == null  ||
                                                                                questionA255Answer == null  ||
                                                                                questionA260Answer == null  ||
                                                                                questionA256Answer == null  ||
                                                                                questionA257Answer == null  ||
                                                                                questionA258Answer == null  ||
                                                                                questionA259Answer == null  ||
                                                                                questionA261Answer == null  ||
                                                                                questionA262Answer == null  ||
                                                                                questionA263Answer == null  ||
                                                                                questionA264Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD31E);
                            baseActivityData.setAlternativeGroup("ALT_E_7_AVAL");
                        baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(toString(questionA245Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_12);
            baseActivityData.setActivityType(ActivityTypeCode.AT_26);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_177);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA254Answer, getUnitByCode(UnitCode.U5135))*toDouble(questionA255Answer, getUnitByCode(UnitCode.U5106))*toDouble(questionA260Answer)+0*(toDouble(questionA256Answer)+toDouble(questionA257Answer)+toDouble(questionA258Answer)+toDouble(questionA259Answer)+toDouble(questionA260Answer)+toDouble(questionA261Answer)+toDouble(questionA262Answer)+toDouble(questionA263Answer)+toDouble(questionA264Answer)));
            res.add(baseActivityData);

            
                         }
                         }
                         }
                         }
                 return res;
    }

}