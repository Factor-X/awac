package eu.factorx.awac.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.type.BooleanAnswerValue;
import eu.factorx.awac.models.data.answer.type.StringAnswerValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.NumericAnswerValue;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.Indicator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.Report;
import eu.factorx.awac.service.FactorService;
import eu.factorx.awac.service.IndicatorService;
import eu.factorx.awac.service.QuestionSetAnswerService;
import eu.factorx.awac.service.ReportService;
import eu.factorx.awac.service.UnitConversionService;
import eu.factorx.awac.service.UnitService;

@Component
public class ReportServiceImpl implements ReportService {

    @Autowired
    private QuestionSetAnswerService questionSetAnswerService;
    @Autowired
    private IndicatorService indicatorService;
    @Autowired
    private FactorService factorService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private UnitConversionService unitConversionService;

    @Override
    public Report getReport(Scope scope, Period period) {

        // find all question set answers (only "parents" => find where qsa.parent is null)
        Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers = getAllQuestionSetAnswers(scope, period);

        // find all activity data
        List<BaseActivityData> activityData = getActivityData(allQuestionSetAnswers);

        // build activity results (== link suitable indicators and factors to each activity data)
        List<BaseActivityResult> activityResults = new ArrayList<>();
        for (BaseActivityData baseActivityData : activityData) {
            List<Indicator> indicators = indicatorService.findCarbonIndicatorsForSitesByActivity(baseActivityData);
            if (indicators.isEmpty()) {
                // TODO save a warning: no indicator for activity base data: ...
            }
            for (Indicator indicator : indicators) {
                Factor factor = factorService.findByIndicatorAndActivity(indicator, baseActivityData);
                if (factor == null) {
                    // TODO save a warning: no factor for activity base data: ... and indicator:...
                } else {
                    activityResults.add(new BaseActivityResult(indicator, baseActivityData, factor));
                }
            }
        }

        return new Report(activityResults);
    }

    private List<BaseActivityData> getActivityData(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();


        res.addAll(getBaseActivityDataAE_BAD1(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD2A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD2B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD3(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD4(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD5(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD40(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD6(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD7A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD7B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD7C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD8A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD8B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD8C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD9A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD9B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD9C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD10(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD11(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12D(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12E(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12F(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12G(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12H(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12I(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12J(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12K(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12L(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12M(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD12N(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD13(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD14(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD15(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD16A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD16B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD16C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD16D(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD17A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD17B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD17C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD17D(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD17E(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD17F(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD17G(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD17H(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD17I(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD18A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD18B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD18C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD18D(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD19A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD19B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD19C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD20(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD21(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD22(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD23(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD24(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD25(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD26A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD26B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD27A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD27B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD27C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD28(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD29A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD29B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD29C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD30(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD31A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD31B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD31C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD31D(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD31E(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD31F(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD31G(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD31H(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD31I(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD32A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD32B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD32C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD32D(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD33A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD33B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD33C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD34A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD34B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD34C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD35A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD35B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD35C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD35D(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD36(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD37A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD37B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD37C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD38A(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD38B(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD38C(allQuestionSetAnswers));
        res.addAll(getBaseActivityDataAE_BAD39A(allQuestionSetAnswers));


        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD1(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
        Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

        // For each set of answers in A15, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A15)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA16Answer = answersByCode.get(QuestionCode.A16);
            QuestionAnswer questionA17Answer = answersByCode.get(QuestionCode.A17);

            if (questionA16Answer == null ||
                    questionA17Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD1);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.ENERGIE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ENERGIE_FOSSILE);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(getCode(questionA16Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA17Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD2A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kWh in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
        Unit baseActivityDataUnit = unitService.findBySymbol("kWh");

        // For each set of answers in A22, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A22)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA24Answer = answersByCode.get(QuestionCode.A24);

            if (questionA24Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD2A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.ENERGIE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ELECTRICITE);
            baseActivityData.setActivityType(ActivityTypeCode.ELEC_PAYS_GRISE);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE_UE27);
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA24Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD2B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kWh in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
        Unit baseActivityDataUnit = unitService.findBySymbol("kWh");

        // For each set of answers in A22, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A22)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA23Answer = answersByCode.get(QuestionCode.A23);

            if (questionA23Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD2B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.ENERGIE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ELECTRICITE);
            baseActivityData.setActivityType(ActivityTypeCode.ELEC_PAYS_VERTE);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE_UE27);
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA23Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD3(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
        Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

        // For each set of answers in A25, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A25)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA26Answer = answersByCode.get(QuestionCode.A26);
            QuestionAnswer questionA28Answer = answersByCode.get(QuestionCode.A28);
            QuestionAnswer questionA27Answer = answersByCode.get(QuestionCode.A27);

            if (questionA26Answer == null ||
                    questionA28Answer == null ||
                    questionA27Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD3);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.ENERGIE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.VAPEUR);
            baseActivityData.setActivityType(ActivityTypeCode.RESEAU_DE_CHALEUR);
            baseActivityData.setActivitySource(getCode(questionA26Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA27Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD4(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (t in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
        Unit baseActivityDataUnit = unitService.findBySymbol("t");

        // For each set of answers in A34, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A34)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA35Answer = answersByCode.get(QuestionCode.A35);
            QuestionAnswer questionA36Answer = answersByCode.get(QuestionCode.A36);

            if (questionA35Answer == null ||
                    questionA36Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD4);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.EMISSION);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.REJET);
            baseActivityData.setActivityType(ActivityTypeCode.GAZ);
            baseActivityData.setActivitySource(getCode(questionA35Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA36Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD5(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kg in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kg)
        Unit baseActivityDataUnit = unitService.findBySymbol("kg");

        // For each set of answers in A42, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A42)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA43Answer = answersByCode.get(QuestionCode.A43);
            QuestionAnswer questionA44Answer = answersByCode.get(QuestionCode.A44);

            if (questionA43Answer == null ||
                    questionA44Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD5);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.EMISSION);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FROID);
            baseActivityData.setActivityType(ActivityTypeCode.GAZ);
            baseActivityData.setActivitySource(getCode(questionA43Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA44Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD40(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kW in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kW)
        Unit baseActivityDataUnit = unitService.findBySymbol("kW");

        // For each set of answers in A45, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A45)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA46Answer = answersByCode.get(QuestionCode.A46);

            if (questionA46Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD40);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.EMISSION);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FROID);
            baseActivityData.setActivityType(ActivityTypeCode.PUISSANCE_DES_BLOCS_FRIGO);
            baseActivityData.setActivitySource(ActivitySourceCode.GENERIQUE);
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA46Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD6(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kW in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kW)
        Unit baseActivityDataUnit = unitService.findBySymbol("kW");

        // For each set of answers in A47, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A47)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA48Answer = answersByCode.get(QuestionCode.A48);
            QuestionAnswer questionA49Answer = answersByCode.get(QuestionCode.A49);

            if (questionA48Answer == null ||
                    questionA49Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD6);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.EMISSION);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FROID);
            baseActivityData.setActivityType(ActivityTypeCode.PUISSANCE_DES_BLOCS_FRIGO);
            baseActivityData.setActivitySource(ActivitySourceCode.GENERIQUE);
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA49Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD7A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A54, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A54)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA55Answer = answersByCode.get(QuestionCode.A55);

            if (questionA55Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD7A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.ESSENCE);
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA55Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD7B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A54, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A54)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA56Answer = answersByCode.get(QuestionCode.A56);

            if (questionA56Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD7B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.DIESEL);
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA56Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD7C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A54, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A54)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA57Answer = answersByCode.get(QuestionCode.A57);

            if (questionA57Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD7C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.GPL);
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA57Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD8A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A58, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A58)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA59Answer = answersByCode.get(QuestionCode.A59);

            if (questionA59Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD8A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DDT);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.ESSENCE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA59Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD8B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A58, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A58)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA60Answer = answersByCode.get(QuestionCode.A60);

            if (questionA60Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD8B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DDT);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.DIESEL);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA60Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD8C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A58, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A58)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA61Answer = answersByCode.get(QuestionCode.A61);

            if (questionA61Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD8C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DDT);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.GPL);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA61Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD9A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A62, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A62)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA63Answer = answersByCode.get(QuestionCode.A63);

            if (questionA63Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD9A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.ESSENCE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA63Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD9B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A62, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A62)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA64Answer = answersByCode.get(QuestionCode.A64);

            if (questionA64Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD9B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.DIESEL);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA64Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD9C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A62, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A62)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA65Answer = answersByCode.get(QuestionCode.A65);

            if (questionA65Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD9C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.GPL);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA65Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD10(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A67, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A67)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA70Answer = answersByCode.get(QuestionCode.A70);
            QuestionAnswer questionA71Answer = answersByCode.get(QuestionCode.A71);
            QuestionAnswer questionA76Answer = answersByCode.get(QuestionCode.A76);
            QuestionAnswer questionA73Answer = answersByCode.get(QuestionCode.A73);
            QuestionAnswer questionA74Answer = answersByCode.get(QuestionCode.A74);
            QuestionAnswer questionA75Answer = answersByCode.get(QuestionCode.A75);
            QuestionAnswer questionA69Answer = answersByCode.get(QuestionCode.A69);
            QuestionAnswer questionA68Answer = answersByCode.get(QuestionCode.A68);

            if (questionA70Answer == null ||
                    questionA71Answer == null ||
                    questionA76Answer == null ||
                    questionA73Answer == null ||
                    questionA74Answer == null ||
                    questionA75Answer == null ||
                    questionA69Answer == null ||
                    questionA68Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD10);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA68Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(getCode(questionA70Answer, ActivitySubCategoryCode.class));
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(getCode(questionA71Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(getValueBoolean(questionA69Answer));
            baseActivityData.setValue(getValue(questionA75Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD11(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A78, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A78)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA81Answer = answersByCode.get(QuestionCode.A81);
            QuestionAnswer questionA83Answer = answersByCode.get(QuestionCode.A83);
            QuestionAnswer questionA88Answer = answersByCode.get(QuestionCode.A88);
            QuestionAnswer questionA89Answer = answersByCode.get(QuestionCode.A89);
            QuestionAnswer questionA90Answer = answersByCode.get(QuestionCode.A90);
            QuestionAnswer questionA91Answer = answersByCode.get(QuestionCode.A91);
            QuestionAnswer questionA92Answer = answersByCode.get(QuestionCode.A92);
            QuestionAnswer questionA80Answer = answersByCode.get(QuestionCode.A80);
            QuestionAnswer questionA79Answer = answersByCode.get(QuestionCode.A79);

            if (questionA81Answer == null ||
                    questionA83Answer == null ||
                    questionA88Answer == null ||
                    questionA89Answer == null ||
                    questionA90Answer == null ||
                    questionA91Answer == null ||
                    questionA92Answer == null ||
                    questionA80Answer == null ||
                    questionA79Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD11);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA79Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(getCode(questionA81Answer, ActivitySubCategoryCode.class));
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(getCode(questionA83Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(getValueBoolean(questionA80Answer));
            baseActivityData.setValue(getValue(questionA92Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA95Answer = answersByCode.get(QuestionCode.A95);

            if (questionA95Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12A);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DDT);
            baseActivityData.setActivityType(ActivityTypeCode.TC_BUS);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA95Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA96Answer = answersByCode.get(QuestionCode.A96);

            if (questionA96Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12B);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.TC_BUS);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA96Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA97Answer = answersByCode.get(QuestionCode.A97);

            if (questionA97Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12C);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DDT);
            baseActivityData.setActivityType(ActivityTypeCode.TC_METRO);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA97Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12D(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA98Answer = answersByCode.get(QuestionCode.A98);

            if (questionA98Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12D);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.TC_METRO);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA98Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12E(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA99Answer = answersByCode.get(QuestionCode.A99);

            if (questionA99Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12E);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DDT);
            baseActivityData.setActivityType(ActivityTypeCode.TC_TRAIN_NATIONAL);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA99Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12F(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA100Answer = answersByCode.get(QuestionCode.A100);

            if (questionA100Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12F);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.TC_TRAIN_NATIONAL);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA100Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12G(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA101Answer = answersByCode.get(QuestionCode.A101);

            if (questionA101Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12G);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DDT);
            baseActivityData.setActivityType(ActivityTypeCode.TC_TRAIN_INTERNATIONAL);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA101Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12H(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA102Answer = answersByCode.get(QuestionCode.A102);

            if (questionA102Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12H);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.TC_TRAIN_INTERNATIONAL);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA102Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12I(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA103Answer = answersByCode.get(QuestionCode.A103);

            if (questionA103Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12I);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DDT);
            baseActivityData.setActivityType(ActivityTypeCode.TC_TRAM);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA103Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12J(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA104Answer = answersByCode.get(QuestionCode.A104);

            if (questionA104Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12J);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.TC_TRAM);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA104Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12K(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (véhicule.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.véhicule.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("véhicule.km");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA105Answer = answersByCode.get(QuestionCode.A105);

            if (questionA105Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12K);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DDT);
            baseActivityData.setActivityType(ActivityTypeCode.TC_TAXI);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA105Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12L(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (véhicule.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.véhicule.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("véhicule.km");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA106Answer = answersByCode.get(QuestionCode.A106);

            if (questionA106Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12L);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.TC_TAXI);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA106Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12M(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (euros in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.euros)
        Unit baseActivityDataUnit = unitService.findBySymbol("euros");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA107Answer = answersByCode.get(QuestionCode.A107);

            if (questionA107Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12M);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DDT);
            baseActivityData.setActivityType(ActivityTypeCode.TC_TAXI);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA107Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD12N(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (euros in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.euros)
        Unit baseActivityDataUnit = unitService.findBySymbol("euros");

        // For each set of answers in A94, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A94)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA108Answer = answersByCode.get(QuestionCode.A108);

            if (questionA108Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD12N);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.TC_TAXI);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA108Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD13(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (employé in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.employé)
        Unit baseActivityDataUnit = unitService.findBySymbol("employé");

        // For each set of answers in A109, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A109)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA110Answer = answersByCode.get(QuestionCode.A110);

            if (questionA110Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD13);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA110Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DDT);
            baseActivityData.setActivityType(ActivityTypeCode.DEPLACEMENT_MOYENNE);
            baseActivityData.setActivitySource(ActivitySourceCode.R);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue("-H", baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD14(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A115, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A115)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA117Answer = answersByCode.get(QuestionCode.A117);
            QuestionAnswer questionA119Answer = answersByCode.get(QuestionCode.A119);
            QuestionAnswer questionA120Answer = answersByCode.get(QuestionCode.A120);
            QuestionAnswer questionA118Answer = answersByCode.get(QuestionCode.A118);
            QuestionAnswer questionA116Answer = answersByCode.get(QuestionCode.A116);

            if (questionA117Answer == null ||
                    questionA119Answer == null ||
                    questionA120Answer == null ||
                    questionA118Answer == null ||
                    questionA116Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD14);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA116Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(getCode(questionA118Answer, ActivityTypeCode.class));
            baseActivityData.setActivitySource(getCode(questionA117Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA120Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD15(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (km.passager in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
        Unit baseActivityDataUnit = unitService.findBySymbol("km.passager");

        // For each set of answers in A121, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A121)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());


            if () {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD15);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.MOBILITE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.DPRO);
            baseActivityData.setActivityType(ActivityTypeCode.AVION_SANS_CLASSE);
            baseActivityData.setActivitySource(ActivitySourceCode.R1);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue("-R2", baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD16A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A132, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A132)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA133Answer = answersByCode.get(QuestionCode.A133);

            if (questionA133Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD16A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.ESSENCE);
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA133Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD16B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A132, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A132)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA134Answer = answersByCode.get(QuestionCode.A134);

            if (questionA134Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD16B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.DIESEL);
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA134Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD16C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A132, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A132)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA135Answer = answersByCode.get(QuestionCode.A135);

            if (questionA135Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD16C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.GPL);
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA135Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD16D(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kg in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kg)
        Unit baseActivityDataUnit = unitService.findBySymbol("kg");

        // For each set of answers in A132, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A132)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA137Answer = answersByCode.get(QuestionCode.A137);
            QuestionAnswer questionA136Answer = answersByCode.get(QuestionCode.A136);
            QuestionAnswer questionA138Answer = answersByCode.get(QuestionCode.A138);
            QuestionAnswer questionA139Answer = answersByCode.get(QuestionCode.A139);
            QuestionAnswer questionA500Answer = answersByCode.get(QuestionCode.A500);

            if (questionA137Answer == null ||
                    questionA136Answer == null ||
                    questionA138Answer == null ||
                    questionA139Answer == null ||
                    questionA500Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD16D);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.GAZ);
            baseActivityData.setActivitySource(getCode(questionA137Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA500Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD17A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A142, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A142)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA145Answer = answersByCode.get(QuestionCode.A145);
            QuestionAnswer questionA146Answer = answersByCode.get(QuestionCode.A146);
            QuestionAnswer questionA147Answer = answersByCode.get(QuestionCode.A147);
            QuestionAnswer questionA143Answer = answersByCode.get(QuestionCode.A143);

            if (questionA145Answer == null ||
                    questionA146Answer == null ||
                    questionA147Answer == null ||
                    questionA143Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD17A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA143Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.CAMION_LOCAL);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA147Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD17B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A142, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A142)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA145Answer = answersByCode.get(QuestionCode.A145);
            QuestionAnswer questionA146Answer = answersByCode.get(QuestionCode.A146);
            QuestionAnswer questionA148Answer = answersByCode.get(QuestionCode.A148);
            QuestionAnswer questionA143Answer = answersByCode.get(QuestionCode.A143);

            if (questionA145Answer == null ||
                    questionA146Answer == null ||
                    questionA148Answer == null ||
                    questionA143Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD17B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA143Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.CAMIONNETTE_LOCAL);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA148Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD17C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A142, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A142)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA145Answer = answersByCode.get(QuestionCode.A145);
            QuestionAnswer questionA146Answer = answersByCode.get(QuestionCode.A146);
            QuestionAnswer questionA149Answer = answersByCode.get(QuestionCode.A149);
            QuestionAnswer questionA143Answer = answersByCode.get(QuestionCode.A143);

            if (questionA145Answer == null ||
                    questionA146Answer == null ||
                    questionA149Answer == null ||
                    questionA143Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD17C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA143Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.CAMION_INTERNATIONAL);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA149Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD17D(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A142, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A142)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA145Answer = answersByCode.get(QuestionCode.A145);
            QuestionAnswer questionA146Answer = answersByCode.get(QuestionCode.A146);
            QuestionAnswer questionA150Answer = answersByCode.get(QuestionCode.A150);
            QuestionAnswer questionA143Answer = answersByCode.get(QuestionCode.A143);

            if (questionA145Answer == null ||
                    questionA146Answer == null ||
                    questionA150Answer == null ||
                    questionA143Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD17D);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA143Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.TRAIN);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA150Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD17E(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A142, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A142)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA145Answer = answersByCode.get(QuestionCode.A145);
            QuestionAnswer questionA146Answer = answersByCode.get(QuestionCode.A146);
            QuestionAnswer questionA151Answer = answersByCode.get(QuestionCode.A151);
            QuestionAnswer questionA143Answer = answersByCode.get(QuestionCode.A143);

            if (questionA145Answer == null ||
                    questionA146Answer == null ||
                    questionA151Answer == null ||
                    questionA143Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD17E);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA143Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.BATEAU);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA151Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD17F(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A142, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A142)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA145Answer = answersByCode.get(QuestionCode.A145);
            QuestionAnswer questionA146Answer = answersByCode.get(QuestionCode.A146);
            QuestionAnswer questionA152Answer = answersByCode.get(QuestionCode.A152);
            QuestionAnswer questionA143Answer = answersByCode.get(QuestionCode.A143);

            if (questionA145Answer == null ||
                    questionA146Answer == null ||
                    questionA152Answer == null ||
                    questionA143Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD17F);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA143Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.BARGE_ / _PENICHE);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA152Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD17G(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A142, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A142)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA145Answer = answersByCode.get(QuestionCode.A145);
            QuestionAnswer questionA146Answer = answersByCode.get(QuestionCode.A146);
            QuestionAnswer questionA153Answer = answersByCode.get(QuestionCode.A153);
            QuestionAnswer questionA143Answer = answersByCode.get(QuestionCode.A143);

            if (questionA145Answer == null ||
                    questionA146Answer == null ||
                    questionA153Answer == null ||
                    questionA143Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD17G);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA143Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.AVION_COURT_COURRIER_( < 1000_KM));
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA153Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD17H(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A142, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A142)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA145Answer = answersByCode.get(QuestionCode.A145);
            QuestionAnswer questionA146Answer = answersByCode.get(QuestionCode.A146);
            QuestionAnswer questionA154Answer = answersByCode.get(QuestionCode.A154);
            QuestionAnswer questionA143Answer = answersByCode.get(QuestionCode.A143);

            if (questionA145Answer == null ||
                    questionA146Answer == null ||
                    questionA154Answer == null ||
                    questionA143Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD17H);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA143Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.AVION_MOYEN_COURRIER_(1000_À_4000_KM));
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA154Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD17I(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A142, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A142)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA145Answer = answersByCode.get(QuestionCode.A145);
            QuestionAnswer questionA146Answer = answersByCode.get(QuestionCode.A146);
            QuestionAnswer questionA155Answer = answersByCode.get(QuestionCode.A155);
            QuestionAnswer questionA143Answer = answersByCode.get(QuestionCode.A143);

            if (questionA145Answer == null ||
                    questionA146Answer == null ||
                    questionA155Answer == null ||
                    questionA143Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD17I);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA143Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.AVION_LONG_COURRIER_( > 4000_KM));
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA155Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD18A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A157, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A157)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA158Answer = answersByCode.get(QuestionCode.A158);

            if (questionA158Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD18A);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose("-ref marchandise");
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.CAMION_(TRANSPORTEUR_EXT));
            baseActivityData.setActivitySource(ActivitySourceCode.ESTIMATION_MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA158Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD18B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A157, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A157)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA158Answer = answersByCode.get(QuestionCode.A158);

            if (questionA158Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD18B);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose("-ref marchandise");
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.CAMION_(TRANSPORTEUR_EXT));
            baseActivityData.setActivitySource(ActivitySourceCode.ESTIMATION_MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA158Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD18C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A157, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A157)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA158Answer = answersByCode.get(QuestionCode.A158);

            if (questionA158Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD18C);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose("-ref marchandise");
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.RAIL / TRAIN / AVION_BELGIQUE);
            baseActivityData.setActivitySource(ActivitySourceCode.ESTIMATION_MIXTE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA158Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD18D(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A157, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A157)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA158Answer = answersByCode.get(QuestionCode.A158);

            if (questionA158Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD18D);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose("-ref marchandise");
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AMONT);
            baseActivityData.setActivityType(ActivityTypeCode.RAIL / TRAIN / AVION_HORS_BELGIQUE_AMONT);
            baseActivityData.setActivitySource(ActivitySourceCode.ESTIMATION_MIXTE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA158Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD19A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
        Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

        // For each set of answers in A157, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A157)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA167Answer = answersByCode.get(QuestionCode.A167);
            QuestionAnswer questionA168Answer = answersByCode.get(QuestionCode.A168);

            if (questionA167Answer == null ||
                    questionA168Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD19A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA167Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.STOCKAGE_AMONT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ENERGIEFOSSILE);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(getCode(questionA167Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA168Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD19B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kWh in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
        Unit baseActivityDataUnit = unitService.findBySymbol("kWh");

        // For each set of answers in A157, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A157)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA160Answer = answersByCode.get(QuestionCode.A160);
            QuestionAnswer questionA167Answer = answersByCode.get(QuestionCode.A167);

            if (questionA160Answer == null ||
                    questionA167Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD19B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA167Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.STOCKAGE_AMONT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ELECTRICITE);
            baseActivityData.setActivityType(ActivityTypeCode.ELEC_PAYS_GRISE);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA160Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD19C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kg in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kg)
        Unit baseActivityDataUnit = unitService.findBySymbol("kg");

        // For each set of answers in A157, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A157)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA171Answer = answersByCode.get(QuestionCode.A171);
            QuestionAnswer questionA172Answer = answersByCode.get(QuestionCode.A172);
            QuestionAnswer questionA167Answer = answersByCode.get(QuestionCode.A167);

            if (questionA171Answer == null ||
                    questionA172Answer == null ||
                    questionA167Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD19C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA167Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.STOCKAGE_AMONT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FROID);
            baseActivityData.setActivityType(ActivityTypeCode.GAZ);
            baseActivityData.setActivitySource(getCode(questionA171Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA172Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD20(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (t in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
        Unit baseActivityDataUnit = unitService.findBySymbol("t");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA177Answer = answersByCode.get(QuestionCode.A177);
            QuestionAnswer questionA179Answer = answersByCode.get(QuestionCode.A179);
            QuestionAnswer questionA178Answer = answersByCode.get(QuestionCode.A178);
            QuestionAnswer questionA176Answer = answersByCode.get(QuestionCode.A176);

            if (questionA177Answer == null ||
                    questionA179Answer == null ||
                    questionA178Answer == null ||
                    questionA176Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD20);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA176Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.DECHET);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(getCode(questionA178Answer, ActivityTypeCode.class));
            baseActivityData.setActivitySource(getCode(questionA177Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(true);
            baseActivityData.setValue(getValue(questionA179Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD21(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (équivalent.habitant in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.équivalent.habitant)
        Unit baseActivityDataUnit = unitService.findBySymbol("équivalent.habitant");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA183Answer = answersByCode.get(QuestionCode.A183);
            QuestionAnswer questionA184Answer = answersByCode.get(QuestionCode.A184);

            if (questionA183Answer == null ||
                    questionA184Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD21);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose("-atelier");
            baseActivityData.setActivityCategory(ActivityCategoryCode.DECHET);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(ActivityTypeCode.EPURATION);
            baseActivityData.setActivitySource(ActivitySourceCode.EAU_USEE);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA184Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD22(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (équivalent.habitant in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.équivalent.habitant)
        Unit baseActivityDataUnit = unitService.findBySymbol("équivalent.habitant");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA186Answer = answersByCode.get(QuestionCode.A186);
            QuestionAnswer questionA187Answer = answersByCode.get(QuestionCode.A187);

            if (questionA186Answer == null ||
                    questionA187Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD22);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose("-bureau");
            baseActivityData.setActivityCategory(ActivityCategoryCode.DECHET);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(ActivityTypeCode.EPURATION);
            baseActivityData.setActivitySource(ActivitySourceCode.EAU_USEE);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA187Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD23(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (équivalent.habitant in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.équivalent.habitant)
        Unit baseActivityDataUnit = unitService.findBySymbol("équivalent.habitant");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA189Answer = answersByCode.get(QuestionCode.A189);
            QuestionAnswer questionA190Answer = answersByCode.get(QuestionCode.A190);

            if (questionA189Answer == null ||
                    questionA190Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD23);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose("-pension");
            baseActivityData.setActivityCategory(ActivityCategoryCode.DECHET);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(ActivityTypeCode.EPURATION);
            baseActivityData.setActivitySource(ActivitySourceCode.EAU_USEE);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA190Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD24(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (équivalent.habitant in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.équivalent.habitant)
        Unit baseActivityDataUnit = unitService.findBySymbol("équivalent.habitant");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA192Answer = answersByCode.get(QuestionCode.A192);
            QuestionAnswer questionA193Answer = answersByCode.get(QuestionCode.A193);

            if (questionA192Answer == null ||
                    questionA193Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD24);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose("-cantine");
            baseActivityData.setActivityCategory(ActivityCategoryCode.DECHET);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(ActivityTypeCode.EPURATION);
            baseActivityData.setActivitySource(ActivitySourceCode.EAU_USEE);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA193Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD25(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (m3 in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.m3)
        Unit baseActivityDataUnit = unitService.findBySymbol("m3");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA198Answer = answersByCode.get(QuestionCode.A198);
            QuestionAnswer questionA199Answer = answersByCode.get(QuestionCode.A199);
            QuestionAnswer questionA200Answer = answersByCode.get(QuestionCode.A200);
            QuestionAnswer questionA195Answer = answersByCode.get(QuestionCode.A195);

            if (questionA198Answer == null ||
                    questionA199Answer == null ||
                    questionA200Answer == null ||
                    questionA195Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD25);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.DECHET);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(getCode(questionA200Answer, ActivityTypeCode.class));
            baseActivityData.setActivitySource(getCode(questionA198Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(getValueBoolean(questionA195Answer));
            baseActivityData.setValue(getValue(questionA199Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD26A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (t in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
        Unit baseActivityDataUnit = unitService.findBySymbol("t");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA202Answer = answersByCode.get(QuestionCode.A202);
            QuestionAnswer questionA203Answer = answersByCode.get(QuestionCode.A203);
            QuestionAnswer questionA501Answer = answersByCode.get(QuestionCode.A501);

            if (questionA202Answer == null ||
                    questionA203Answer == null ||
                    questionA501Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD26A);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.DECHET);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(ActivityTypeCode.DCO);
            baseActivityData.setActivitySource(getCode(questionA202Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(getValueBoolean(questionA501Answer));
            baseActivityData.setValue(getValue(questionA203Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD26B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (t in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
        Unit baseActivityDataUnit = unitService.findBySymbol("t");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA203Answer = answersByCode.get(QuestionCode.A203);
            QuestionAnswer questionA204Answer = answersByCode.get(QuestionCode.A204);
            QuestionAnswer questionA501Answer = answersByCode.get(QuestionCode.A501);

            if (questionA203Answer == null ||
                    questionA204Answer == null ||
                    questionA501Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD26B);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.DECHET);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(ActivityTypeCode.AZOTE);
            baseActivityData.setActivitySource(getCode(questionA203Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership(getValueBoolean(questionA501Answer));
            baseActivityData.setValue(getValue(questionA204Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD27A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (t in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
        Unit baseActivityDataUnit = unitService.findBySymbol("t");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA212Answer = answersByCode.get(QuestionCode.A212);
            QuestionAnswer questionA213Answer = answersByCode.get(QuestionCode.A213);
            QuestionAnswer questionA214Answer = answersByCode.get(QuestionCode.A214);
            QuestionAnswer questionA215Answer = answersByCode.get(QuestionCode.A215);
            QuestionAnswer questionA216Answer = answersByCode.get(QuestionCode.A216);
            QuestionAnswer questionA217Answer = answersByCode.get(QuestionCode.A217);
            QuestionAnswer questionA218Answer = answersByCode.get(QuestionCode.A218);
            QuestionAnswer questionA220Answer = answersByCode.get(QuestionCode.A220);
            QuestionAnswer questionA221Answer = answersByCode.get(QuestionCode.A221);
            QuestionAnswer questionA211Answer = answersByCode.get(QuestionCode.A211);
            QuestionAnswer questionA210Answer = answersByCode.get(QuestionCode.A210);

            if (questionA212Answer == null ||
                    questionA213Answer == null ||
                    questionA214Answer == null ||
                    questionA215Answer == null ||
                    questionA216Answer == null ||
                    questionA217Answer == null ||
                    questionA218Answer == null ||
                    questionA220Answer == null ||
                    questionA221Answer == null ||
                    questionA211Answer == null ||
                    questionA210Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD27A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA210Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.ACHAT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(getCode(questionA211Answer, ActivityTypeCode.class));
            baseActivityData.setActivitySource(getCode(questionA218Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA221Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD27B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (t in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
        Unit baseActivityDataUnit = unitService.findBySymbol("t");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA212Answer = answersByCode.get(QuestionCode.A212);
            QuestionAnswer questionA213Answer = answersByCode.get(QuestionCode.A213);
            QuestionAnswer questionA214Answer = answersByCode.get(QuestionCode.A214);
            QuestionAnswer questionA215Answer = answersByCode.get(QuestionCode.A215);
            QuestionAnswer questionA216Answer = answersByCode.get(QuestionCode.A216);
            QuestionAnswer questionA217Answer = answersByCode.get(QuestionCode.A217);
            QuestionAnswer questionA218Answer = answersByCode.get(QuestionCode.A218);
            QuestionAnswer questionA220Answer = answersByCode.get(QuestionCode.A220);
            QuestionAnswer questionA221Answer = answersByCode.get(QuestionCode.A221);
            QuestionAnswer questionA211Answer = answersByCode.get(QuestionCode.A211);
            QuestionAnswer questionA210Answer = answersByCode.get(QuestionCode.A210);

            if (questionA212Answer == null ||
                    questionA213Answer == null ||
                    questionA214Answer == null ||
                    questionA215Answer == null ||
                    questionA216Answer == null ||
                    questionA217Answer == null ||
                    questionA218Answer == null ||
                    questionA220Answer == null ||
                    questionA221Answer == null ||
                    questionA211Answer == null ||
                    questionA210Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD27B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA210Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.ACHAT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(getCode(questionA211Answer, ActivityTypeCode.class));
            baseActivityData.setActivitySource(getCode(questionA218Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA221Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD27C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (euros in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.euros)
        Unit baseActivityDataUnit = unitService.findBySymbol("euros");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA219Answer = answersByCode.get(QuestionCode.A219);
            QuestionAnswer questionA222Answer = answersByCode.get(QuestionCode.A222);
            QuestionAnswer questionA210Answer = answersByCode.get(QuestionCode.A210);

            if (questionA219Answer == null ||
                    questionA222Answer == null ||
                    questionA210Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD27C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA210Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.ACHAT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(ActivityTypeCode.SERVICES_ET_INFORMATIQUE);
            baseActivityData.setActivitySource(getCode(questionA219Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA222Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD28(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tCO2e in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tCO2e)
        Unit baseActivityDataUnit = unitService.findBySymbol("tCO2e");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA227Answer = answersByCode.get(QuestionCode.A227);
            QuestionAnswer questionA228Answer = answersByCode.get(QuestionCode.A228);
            QuestionAnswer questionA225Answer = answersByCode.get(QuestionCode.A225);

            if (questionA227Answer == null ||
                    questionA228Answer == null ||
                    questionA225Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD28);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA225Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.ACHAT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FACTEUR_PROPRE);
            baseActivityData.setActivityType(ActivityTypeCode.DIRECT_(CO2E));
            baseActivityData.setActivitySource(ActivitySourceCode.DIRECT_(CO2E));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA228Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD29A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (m2 in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.m2)
        Unit baseActivityDataUnit = unitService.findBySymbol("m2");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA233Answer = answersByCode.get(QuestionCode.A233);
            QuestionAnswer questionA234Answer = answersByCode.get(QuestionCode.A234);
            QuestionAnswer questionA232Answer = answersByCode.get(QuestionCode.A232);

            if (questionA233Answer == null ||
                    questionA234Answer == null ||
                    questionA232Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD29A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA232Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.INFRASTRUCTURE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(getCode(questionA233Answer, ActivityTypeCode.class));
            baseActivityData.setActivitySource(getCode(questionA233Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA234Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD29B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (t in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
        Unit baseActivityDataUnit = unitService.findBySymbol("t");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA233Answer = answersByCode.get(QuestionCode.A233);
            QuestionAnswer questionA235Answer = answersByCode.get(QuestionCode.A235);
            QuestionAnswer questionA232Answer = answersByCode.get(QuestionCode.A232);

            if (questionA233Answer == null ||
                    questionA235Answer == null ||
                    questionA232Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD29B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA232Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.INFRASTRUCTURE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(getCode(questionA233Answer, ActivityTypeCode.class));
            baseActivityData.setActivitySource(getCode(questionA233Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA235Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD29C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (Unit in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.Unit)
        Unit baseActivityDataUnit = unitService.findBySymbol("Unit");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA233Answer = answersByCode.get(QuestionCode.A233);
            QuestionAnswer questionA236Answer = answersByCode.get(QuestionCode.A236);
            QuestionAnswer questionA232Answer = answersByCode.get(QuestionCode.A232);

            if (questionA233Answer == null ||
                    questionA236Answer == null ||
                    questionA232Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD29C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA232Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.INFRASTRUCTURE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(getCode(questionA233Answer, ActivityTypeCode.class));
            baseActivityData.setActivitySource(getCode(questionA233Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA236Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD30(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tCO2e in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tCO2e)
        Unit baseActivityDataUnit = unitService.findBySymbol("tCO2e");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA241Answer = answersByCode.get(QuestionCode.A241);
            QuestionAnswer questionA242Answer = answersByCode.get(QuestionCode.A242);
            QuestionAnswer questionA239Answer = answersByCode.get(QuestionCode.A239);

            if (questionA241Answer == null ||
                    questionA242Answer == null ||
                    questionA239Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD30);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA239Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.INFRASTRUCTURE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FACTEUR_PROPRE);
            baseActivityData.setActivityType(ActivityTypeCode.DIRECT_(CO2E));
            baseActivityData.setActivitySource(ActivitySourceCode.DIRECT_(CO2E));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA242Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD31A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA239Answer = answersByCode.get(QuestionCode.A239);
            QuestionAnswer questionA240Answer = answersByCode.get(QuestionCode.A240);
            QuestionAnswer questionA241Answer = answersByCode.get(QuestionCode.A241);

            if (questionA239Answer == null ||
                    questionA240Answer == null ||
                    questionA241Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD31A);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA239Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.CAMION_LOCAL);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA241Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD31B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA239Answer = answersByCode.get(QuestionCode.A239);
            QuestionAnswer questionA240Answer = answersByCode.get(QuestionCode.A240);
            QuestionAnswer questionA242Answer = answersByCode.get(QuestionCode.A242);

            if (questionA239Answer == null ||
                    questionA240Answer == null ||
                    questionA242Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD31B);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA239Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.CAMIONNETTE_LOCAL);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA242Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD31C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA239Answer = answersByCode.get(QuestionCode.A239);
            QuestionAnswer questionA240Answer = answersByCode.get(QuestionCode.A240);
            QuestionAnswer questionA236Answer = answersByCode.get(QuestionCode.A236);

            if (questionA239Answer == null ||
                    questionA240Answer == null ||
                    questionA236Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD31C);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA239Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.CAMION_INTERNATIONAL);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA236Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD31D(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA239Answer = answersByCode.get(QuestionCode.A239);
            QuestionAnswer questionA240Answer = answersByCode.get(QuestionCode.A240);
            QuestionAnswer questionA215Answer = answersByCode.get(QuestionCode.A215);

            if (questionA239Answer == null ||
                    questionA240Answer == null ||
                    questionA215Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD31D);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA239Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.TRAIN);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA215Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD31E(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA239Answer = answersByCode.get(QuestionCode.A239);
            QuestionAnswer questionA240Answer = answersByCode.get(QuestionCode.A240);
            QuestionAnswer questionA216Answer = answersByCode.get(QuestionCode.A216);

            if (questionA239Answer == null ||
                    questionA240Answer == null ||
                    questionA216Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD31E);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA239Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.BATEAU);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA216Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD31F(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA239Answer = answersByCode.get(QuestionCode.A239);
            QuestionAnswer questionA240Answer = answersByCode.get(QuestionCode.A240);
            QuestionAnswer questionA217Answer = answersByCode.get(QuestionCode.A217);

            if (questionA239Answer == null ||
                    questionA240Answer == null ||
                    questionA217Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD31F);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA239Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.BARGE_ / _PENICHE);
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA217Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD31G(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA239Answer = answersByCode.get(QuestionCode.A239);
            QuestionAnswer questionA240Answer = answersByCode.get(QuestionCode.A240);
            QuestionAnswer questionA218Answer = answersByCode.get(QuestionCode.A218);

            if (questionA239Answer == null ||
                    questionA240Answer == null ||
                    questionA218Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD31G);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA239Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.AVION_COURT_COURRIER_( < 1000_KM));
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA218Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD31H(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA239Answer = answersByCode.get(QuestionCode.A239);
            QuestionAnswer questionA240Answer = answersByCode.get(QuestionCode.A240);
            QuestionAnswer questionA219Answer = answersByCode.get(QuestionCode.A219);

            if (questionA239Answer == null ||
                    questionA240Answer == null ||
                    questionA219Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD31H);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA239Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.AVION_MOYEN_COURRIER_(1000_À_4000_KM));
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA219Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD31I(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA239Answer = answersByCode.get(QuestionCode.A239);
            QuestionAnswer questionA240Answer = answersByCode.get(QuestionCode.A240);
            QuestionAnswer questionA220Answer = answersByCode.get(QuestionCode.A220);

            if (questionA239Answer == null ||
                    questionA240Answer == null ||
                    questionA220Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD31I);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(getValueString(questionA239Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.AVION_LONG_COURRIER_( > 4000_KM));
            baseActivityData.setActivitySource(ActivitySourceCode.MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA220Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD32A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA267Answer = answersByCode.get(QuestionCode.A267);

            if (questionA267Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD32A);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(getValueString(questionA267Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.CAMION_(TRANSPORTEUR_EXT));
            baseActivityData.setActivitySource(ActivitySourceCode.ESTIMATION_MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA267Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD32B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (l in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
        Unit baseActivityDataUnit = unitService.findBySymbol("l");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA267Answer = answersByCode.get(QuestionCode.A267);

            if (questionA267Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD32B);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(getValueString(questionA267Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.CAMION_(TRANSPORTEUR_EXT));
            baseActivityData.setActivitySource(ActivitySourceCode.ESTIMATION_MOYENNE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA267Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD32C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA267Answer = answersByCode.get(QuestionCode.A267);

            if (questionA267Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD32C);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(getValueString(questionA267Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.RAIL / TRAIN / AVION_BELGIQUE);
            baseActivityData.setActivitySource(ActivitySourceCode.ESTIMATION_MIXTE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA267Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD32D(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tonne.km in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
        Unit baseActivityDataUnit = unitService.findBySymbol("tonne.km");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA267Answer = answersByCode.get(QuestionCode.A267);

            if (questionA267Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD32D);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(getValueString(questionA267Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRANSPORT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.AVAL);
            baseActivityData.setActivityType(ActivityTypeCode.RAIL / TRAIN / AVION_HORS_BELGIQUE_AVAL);
            baseActivityData.setActivitySource(ActivitySourceCode.ESTIMATION_MIXTE);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setValue(getValue(questionA267Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD33A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
        Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA276Answer = answersByCode.get(QuestionCode.A276);
            QuestionAnswer questionA277Answer = answersByCode.get(QuestionCode.A277);

            if (questionA276Answer == null ||
                    questionA277Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD33A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA276Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.STOCKAGE_AVAL);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ENERGIEFOSSILE);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(getCode(questionA276Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA277Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD33B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kWh in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
        Unit baseActivityDataUnit = unitService.findBySymbol("kWh");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA278Answer = answersByCode.get(QuestionCode.A278);
            QuestionAnswer questionA276Answer = answersByCode.get(QuestionCode.A276);

            if (questionA278Answer == null ||
                    questionA276Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD33B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA276Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.STOCKAGE_AVAL);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ELECTRICITE);
            baseActivityData.setActivityType(ActivityTypeCode.ELEC_PAYS_GRISE);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA278Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD33C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kg in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kg)
        Unit baseActivityDataUnit = unitService.findBySymbol("kg");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA280Answer = answersByCode.get(QuestionCode.A280);
            QuestionAnswer questionA281Answer = answersByCode.get(QuestionCode.A281);
            QuestionAnswer questionA276Answer = answersByCode.get(QuestionCode.A276);

            if (questionA280Answer == null ||
                    questionA281Answer == null ||
                    questionA276Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD33C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA276Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.STOCKAGE_AVAL);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FROID);
            baseActivityData.setActivityType(ActivityTypeCode.GAZ);
            baseActivityData.setActivitySource(getCode(questionA280Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA281Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD34A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
        Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA285Answer = answersByCode.get(QuestionCode.A285);
            QuestionAnswer questionA286Answer = answersByCode.get(QuestionCode.A286);

            if (questionA285Answer == null ||
                    questionA286Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD34A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA285Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRAITEMENT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ENERGIEFOSSILE);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(getCode(questionA285Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA286Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD34B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kWh in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
        Unit baseActivityDataUnit = unitService.findBySymbol("kWh");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA287Answer = answersByCode.get(QuestionCode.A287);
            QuestionAnswer questionA285Answer = answersByCode.get(QuestionCode.A285);

            if (questionA287Answer == null ||
                    questionA285Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD34B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA285Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRAITEMENT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ELECTRICITE);
            baseActivityData.setActivityType(ActivityTypeCode.ELEC_PAYS_GRISE);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA287Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD34C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kg in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kg)
        Unit baseActivityDataUnit = unitService.findBySymbol("kg");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA289Answer = answersByCode.get(QuestionCode.A289);
            QuestionAnswer questionA290Answer = answersByCode.get(QuestionCode.A290);
            QuestionAnswer questionA285Answer = answersByCode.get(QuestionCode.A285);

            if (questionA289Answer == null ||
                    questionA290Answer == null ||
                    questionA285Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD34C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA285Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.TRAITEMENT);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FROID);
            baseActivityData.setActivityType(ActivityTypeCode.GAZ);
            baseActivityData.setActivitySource(getCode(questionA289Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA290Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD35A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
        Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA294Answer = answersByCode.get(QuestionCode.A294);
            QuestionAnswer questionA293Answer = answersByCode.get(QuestionCode.A293);

            if (questionA294Answer == null ||
                    questionA293Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD35A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA293Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.UTILISATION);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ENERGIEFOSSILE);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.DIESEL);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA293Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD35B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
        Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA294Answer = answersByCode.get(QuestionCode.A294);
            QuestionAnswer questionA295Answer = answersByCode.get(QuestionCode.A295);
            QuestionAnswer questionA293Answer = answersByCode.get(QuestionCode.A293);

            if (questionA294Answer == null ||
                    questionA295Answer == null ||
                    questionA293Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD35B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA293Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.UTILISATION);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ENERGIEFOSSILE);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(ActivitySourceCode.ESSENCE);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA293Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD35C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kWh in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
        Unit baseActivityDataUnit = unitService.findBySymbol("kWh");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA294Answer = answersByCode.get(QuestionCode.A294);
            QuestionAnswer questionA296Answer = answersByCode.get(QuestionCode.A296);
            QuestionAnswer questionA293Answer = answersByCode.get(QuestionCode.A293);

            if (questionA294Answer == null ||
                    questionA296Answer == null ||
                    questionA293Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD35C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA293Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.UTILISATION);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ELECTRICITE);
            baseActivityData.setActivityType(ActivityTypeCode.ELEC_PAYS_GRISE);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA293Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD35D(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kg in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kg)
        Unit baseActivityDataUnit = unitService.findBySymbol("kg");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA298Answer = answersByCode.get(QuestionCode.A298);
            QuestionAnswer questionA294Answer = answersByCode.get(QuestionCode.A294);
            QuestionAnswer questionA299Answer = answersByCode.get(QuestionCode.A299);
            QuestionAnswer questionA293Answer = answersByCode.get(QuestionCode.A293);

            if (questionA298Answer == null ||
                    questionA294Answer == null ||
                    questionA299Answer == null ||
                    questionA293Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD35D);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA293Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.UTILISATION);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FROID);
            baseActivityData.setActivityType(ActivityTypeCode.GAZ);
            baseActivityData.setActivitySource(getCode(questionA298Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA293Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD36(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (t in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
        Unit baseActivityDataUnit = unitService.findBySymbol("t");

        // For each set of answers in A164, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A164)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA305Answer = answersByCode.get(QuestionCode.A305);
            QuestionAnswer questionA308Answer = answersByCode.get(QuestionCode.A308);
            QuestionAnswer questionA306Answer = answersByCode.get(QuestionCode.A306);
            QuestionAnswer questionA304Answer = answersByCode.get(QuestionCode.A304);

            if (questionA305Answer == null ||
                    questionA308Answer == null ||
                    questionA306Answer == null ||
                    questionA304Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD36);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(getValueString(questionA304Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.FIN_DE_VIE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.MATIERE);
            baseActivityData.setActivityType(getCode(questionA306Answer, ActivityTypeCode.class));
            baseActivityData.setActivitySource(getCode(questionA305Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA305Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD37A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
        Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

        // For each set of answers in A291, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A291)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA314Answer = answersByCode.get(QuestionCode.A314);
            QuestionAnswer questionA315Answer = answersByCode.get(QuestionCode.A315);

            if (questionA314Answer == null ||
                    questionA315Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD37A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA314Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.ACTIF_LOUE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ENERGIEFOSSILE);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(getCode(questionA314Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA315Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD37B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kWh in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
        Unit baseActivityDataUnit = unitService.findBySymbol("kWh");

        // For each set of answers in A291, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A291)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA316Answer = answersByCode.get(QuestionCode.A316);
            QuestionAnswer questionA314Answer = answersByCode.get(QuestionCode.A314);

            if (questionA316Answer == null ||
                    questionA314Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD37B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA314Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.ACTIF_LOUE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ELECTRICITE);
            baseActivityData.setActivityType(ActivityTypeCode.ELEC_PAYS_GRISE);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA316Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD37C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kg in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kg)
        Unit baseActivityDataUnit = unitService.findBySymbol("kg");

        // For each set of answers in A291, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A291)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA318Answer = answersByCode.get(QuestionCode.A318);
            QuestionAnswer questionA319Answer = answersByCode.get(QuestionCode.A319);
            QuestionAnswer questionA314Answer = answersByCode.get(QuestionCode.A314);

            if (questionA318Answer == null ||
                    questionA319Answer == null ||
                    questionA314Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD37C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA314Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.ACTIF_LOUE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FROID);
            baseActivityData.setActivityType(ActivityTypeCode.GAZ);
            baseActivityData.setActivitySource(getCode(questionA318Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA319Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD38A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
        Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

        // For each set of answers in A291, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A291)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA326Answer = answersByCode.get(QuestionCode.A326);
            QuestionAnswer questionA327Answer = answersByCode.get(QuestionCode.A327);

            if (questionA326Answer == null ||
                    questionA327Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD38A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA326Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.FRANCHISE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ENERGIEFOSSILE);
            baseActivityData.setActivityType(ActivityTypeCode.COMBUSTION_FOSSILE);
            baseActivityData.setActivitySource(getCode(questionA326Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA327Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD38B(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kWh in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
        Unit baseActivityDataUnit = unitService.findBySymbol("kWh");

        // For each set of answers in A291, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A291)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA328Answer = answersByCode.get(QuestionCode.A328);
            QuestionAnswer questionA327Answer = answersByCode.get(QuestionCode.A327);
            QuestionAnswer questionA326Answer = answersByCode.get(QuestionCode.A326);

            if (questionA328Answer == null ||
                    questionA327Answer == null ||
                    questionA326Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD38B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA326Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.FRANCHISE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ELECTRICITE);
            baseActivityData.setActivityType(ActivityTypeCode.ELEC_PAYS_GRISE);
            baseActivityData.setActivitySource(ActivitySourceCode.BELGIQUE);
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA327Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD38C(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (kg in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kg)
        Unit baseActivityDataUnit = unitService.findBySymbol("kg");

        // For each set of answers in A291, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A291)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA330Answer = answersByCode.get(QuestionCode.A330);
            QuestionAnswer questionA331Answer = answersByCode.get(QuestionCode.A331);
            QuestionAnswer questionA327Answer = answersByCode.get(QuestionCode.A327);
            QuestionAnswer questionA326Answer = answersByCode.get(QuestionCode.A326);

            if (questionA330Answer == null ||
                    questionA331Answer == null ||
                    questionA327Answer == null ||
                    questionA326Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD38C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA326Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.FRANCHISE);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FROID);
            baseActivityData.setActivityType(ActivityTypeCode.GAZ);
            baseActivityData.setActivitySource(getCode(questionA330Answer, ActivitySourceCode.class));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA327Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }

    private List<BaseActivityData> getBaseActivityDataAE_BAD39A(Map<QuestionCode, List<QuestionSetAnswer>> allQuestionSetAnswers) {
        List<BaseActivityData> res = new ArrayList<>();

        // Get Target Unit (tCO2e in this case)
        // Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tCO2e)
        Unit baseActivityDataUnit = unitService.findBySymbol("tCO2e");

        // For each set of answers in A291, build an ActivityBaseData (see specifications)
        for (QuestionSetAnswer questionSetAnswers : allQuestionSetAnswers.get(QuestionCode.A291)) {

            Map<QuestionCode, QuestionAnswer> answersByCode = toQuestionAnswersByQuestionCodeMap(questionSetAnswers.getQuestionAnswers());

            QuestionAnswer questionA336Answer = answersByCode.get(QuestionCode.A336);
            QuestionAnswer questionA337Answer = answersByCode.get(QuestionCode.A337);
            QuestionAnswer questionA335Answer = answersByCode.get(QuestionCode.A335);

            if (questionA336Answer == null ||
                    questionA337Answer == null ||
                    questionA335Answer == null) {
                continue;
            }


            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD39A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(getValueString(questionA335Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.INVESTISSEMENT_SCOPE_1);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.FACTEUR_PROPRE);
            baseActivityData.setActivityType(ActivityTypeCode.DIRECT_(CO2E));
            baseActivityData.setActivitySource(ActivitySourceCode.DIRECT_(CO2E));
            baseActivityData.setActivityOwnership("-");
            baseActivityData.setValue(getValue(questionA337Answer, baseActivityDataUnit));

            res.add(baseActivityData);
        }
        return res;
    }


    private Map<QuestionCode, QuestionAnswer> toQuestionAnswersByQuestionCodeMap(List<QuestionAnswer> questionAnswers) {
        Map<QuestionCode, QuestionAnswer> res = new HashMap<>();
        for (QuestionAnswer questionAnswer : questionAnswers) {
            res.put(questionAnswer.getQuestion().getCode(), questionAnswer);
        }
        return res;
    }

    private Map<QuestionCode, List<QuestionSetAnswer>> getAllQuestionSetAnswers(Scope scope, Period period) {
        List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByScopeAndPeriod(scope, period);

        Map<QuestionCode, List<QuestionSetAnswer>> res = new HashMap<>();
        for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
            QuestionCode code = questionSetAnswer.getQuestionSet().getCode();
            if (!res.containsKey(code)) {
                res.put(code, new ArrayList<QuestionSetAnswer>());
            }
            res.get(code).add(questionSetAnswer);
        }

        return res;
    }

    private <T extends Code> T getCode(QuestionAnswer questionAnswer, Class<T> codeClass) {
        if (questionAnswer == null) {
            return null;
        }
        CodeAnswerValue answerValue = (CodeAnswerValue) questionAnswer.getAnswerValues().get(0);

        T code = null;
        // TODO find a better way to instantiate a new Code impl!
        try {
            code = codeClass.getConstructor(String.class).newInstance(answerValue.getValue().getKey());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Unable to build a new " + codeClass, e);
        }
        return code;
    }

    private Double getValue(QuestionAnswer questionAnswer, Unit unit) {
        NumericAnswerValue numericAnswerValue = (NumericAnswerValue) questionAnswer.getAnswerValues().get(0);
        Unit answerUnit = numericAnswerValue.getUnit();
        Double answerValue = numericAnswerValue.doubleValue();
        if (!answerUnit.equals(unit)) {
            answerValue = convertValue(answerValue, answerUnit, unit);
        }
        return answerValue;
    }


    private String getValueString(QuestionAnswer questionAnswer) {

        StringAnswerValue stringAnswerValue = (StringAnswerValue) questionAnswer.getAnswerValues().get(0);
        String answerValue = stringAnswerValue.getValue();
        return answerValue;
    }


    private Boolean getValueBoolean(QuestionAnswer questionAnswer) {

        BooleanAnswerValue stringAnswerValue = (BooleanAnswerValue) questionAnswer.getAnswerValues().get(0);
        Boolean answerValue = stringAnswerValue.getValue();
        return answerValue;
    }

    private Double convertValue(Double answerValue, Unit unitFrom, Unit toUnit) {
        return unitConversionService.convert(answerValue, unitFrom, toUnit, null);
    }

}
