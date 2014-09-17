package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.service.QuestionService;
import eu.factorx.awac.util.MyrmexRuntimeException;
import eu.factorx.awac.util.data.importer.ExcelEquivalenceColumn;
import eu.factorx.awac.util.data.importer.WorkbookDataImporter;
import eu.factorx.awac.util.data.importer.badImporter.Reader.Data;
import eu.factorx.awac.util.data.importer.badImporter.Reader.ExcelReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import play.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by florian on 29/08/14.
 * <p/>
 * !! the column F of the excel file is used to detect a bad : if the column is not empty, it's a BAD !!
 */
@Component
public class BADImporter extends WorkbookDataImporter implements ApplicationContextAware {

    private final static String FILE_PATH = "data_importer_resources/awac_data_09-08-2014/AWAC-tous-calcul_FE.xls";
    public static final String ENTERPRISE_METHOD = "site entreprise-activityData";
    public static final String MUNICIPALIT_METHOD = "commune-activityData";

    private final static boolean DEBUG = false;

    private final static int TOTAL_COL = ExcelEquivalenceColumn.T;

    //only string => ref to a BaseActivityDataKey
    private final static int BAD_KEY_COL = ExcelEquivalenceColumn.B;

    //only string
    private final static int BAD_NAME_COL = ExcelEquivalenceColumn.C;

    //only integer
    private final static int BAD_RANK_COL = ExcelEquivalenceColumn.E;

    //can be an answer
    private final static int BAD_SPECIFIC_PURPOSE_COL = ExcelEquivalenceColumn.F;

    //condition
    private final static int BAD_CONDITION_COL = ExcelEquivalenceColumn.G;

    //ActivityCategoryKey
    private final static int BAD_ACTIVITY_CATEGORY_KEY_COL = ExcelEquivalenceColumn.H;

    //ActivitySubCategoryKey
    private final static int BAD_ACTIVITY_SUB_CATEGORY_KEY_COL = ExcelEquivalenceColumn.J;

    //ActivityTypeKey
    private final static int BAD_ACTIVITY_TYPE_KEY_COL = ExcelEquivalenceColumn.L;

    //ActivitySourceKey or answer
    private final static int BAD_ACTIVITY_SOURCE_KEY_COL = ExcelEquivalenceColumn.N;

    //UnitCode
    private final static int BAD_UNIT_COL = ExcelEquivalenceColumn.Q;

    //activityOwnerShip : boolean
    private final static int BAD_ACTIVITY_OWNERSHIP_COL = ExcelEquivalenceColumn.P;

    //value
    private final static int BAD_VALUE_COL = ExcelEquivalenceColumn.S;

    //test value
    private final static int TEST_VALUE_COL = ExcelEquivalenceColumn.X;

    //private

    @Autowired
    private QuestionService questionService;

    @Autowired
    private BADControlElement badControlElement;

    private ApplicationContext ctx = null;

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // Assign the ApplicationContext into a static method
        this.ctx = ctx;
    }


    public BADLog importBAD(InterfaceTypeCode interfaceTypeCode) {
        BADLog badLog = new BADLog();
        try {

            importDatas(badLog,interfaceTypeCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return badLog;
    }



    @Override
    protected void importData() throws Exception {
        //useless
    }

    protected void importDatas(BADLog badLog,InterfaceTypeCode interfaceTypeCode) throws Exception {

        play.Logger.info("run badimporter....");


        ExcelReader excelReader = new ExcelReader();

        Data data =null;
                TemplateName templateName=null;
        if(interfaceTypeCode.equals(InterfaceTypeCode.ENTERPRISE)) {
            data = excelReader.readFile(FILE_PATH, ENTERPRISE_METHOD);
            templateName = TemplateName.BAD_ENTERPRISE;
            Logger.info("ENTERRPISEE !!");
        }
        else if(interfaceTypeCode.equals(InterfaceTypeCode.MUNICIPALITY)){
            data = excelReader.readFile(FILE_PATH, MUNICIPALIT_METHOD);
            templateName = TemplateName.BAD_MUNICIPALITY;
            Logger.info("ENTERRPISEE !!");
        }
        else{
            throw new MyrmexRuntimeException("Cannot found the interface");
        }

        //2. read
        reader(data, badLog,templateName);

        play.Logger.info("run badimporter end !");

    }


    //not null, ActivityType or answer(control list content) or more complex

    public void reader(Data data, BADLog badLog, TemplateName templateName) {



        Map<String, Answer> mapAnswer = new HashMap<>();

        List<BAD> bads = new ArrayList<>();
        

        for (int line = 1; line < data.getNbRows()+1; line++) {

            //escape the first line : presentation
            if (line == 1) {
                continue;
            }

            //load question for response
            if (data.getData(ExcelEquivalenceColumn.C, line) != null &&
                    data.getData(ExcelEquivalenceColumn.C, line).equals("1") &&
                    data.getData(TEST_VALUE_COL, line) != null) {

                String questionCode = data.getData(ExcelEquivalenceColumn.I, line);

                //create new logLine
                BADLog.LogLine logLine = badLog.getLogLine(line, BADLog.LogCat.QUESTION, questionCode);
                badControlElement.setBadLog(logLine);

                //add question
                Question question = questionService.findByCode(new QuestionCode(questionCode));

                if (question == null) {
                    logLine.addError("Try to load the question " + questionCode + " but not found");
                } else {

                    //add value
                    mapAnswer.put(question.getCode().getKey(), badControlElement.controlAnswerValue(question, data.getData(ExcelEquivalenceColumn.X, line)));

                }
            }

            //F is the reference column => not empty = it's a BAD !
            if (data.getData(ExcelEquivalenceColumn.B, line) != null &&
                    !data.getData(ExcelEquivalenceColumn.B, line).equals("BAD-KEY") &&
                    data.getData(ExcelEquivalenceColumn.B, line).contains("BAD")) {

                //create logLine
                BADLog.LogLine logLine = badLog.getLogLine(line, BADLog.LogCat.BAD, data.getData(ExcelEquivalenceColumn.B, line)+" - "+data.getData(ExcelEquivalenceColumn.C, line));
                badControlElement.setBadLog(logLine);

                // activity data founded

                //create the bad
                BAD bad = new BAD();

                bad.setLine(line);


                // --- test badKey ---
                badControlElement.controlBADKey(data.getData(BAD_KEY_COL, line),  bad);

                // ---- name ----
                badControlElement.controlName(data.getData(BAD_NAME_COL, line),  bad);

                // ---- control rank ---
                badControlElement.controlRank(data.getData(BAD_RANK_COL, line),  bad);

                // ---- unit ---
                badControlElement.controlUnit(data.getData(BAD_UNIT_COL, line),  bad);

                // ---- specific purpose ----
                badControlElement.controlSpecificPurpose(data.getData(BAD_SPECIFIC_PURPOSE_COL, line),  bad);

                // ---- activityCategory ---
                badControlElement.controlActivityCategory(data.getData(BAD_ACTIVITY_CATEGORY_KEY_COL, line),  bad);

                // --- activitySubCategory ---
                badControlElement.controlActivitySubCategory(data.getData(BAD_ACTIVITY_SUB_CATEGORY_KEY_COL, line),  bad);

                // --- ActivityType ---
                badControlElement.controlActivityType(data.getData(BAD_ACTIVITY_TYPE_KEY_COL, line),  bad);

                // --- ActivitySource ---
                badControlElement.controlActivitySource(data.getData(BAD_ACTIVITY_SOURCE_KEY_COL, line),  bad);

                // --- activityOwnerShip ---
                badControlElement.controlActivityOwnerShip(data.getData(BAD_ACTIVITY_OWNERSHIP_COL, line),  bad);

                // --- value ---
                badControlElement.controlValue(data.getData(BAD_VALUE_COL, line),  bad);

                // ----- condition ---
                badControlElement.controlCondition(data.getData(BAD_CONDITION_COL, line),  bad);

                // -------- control test value --------
                badControlElement.controlTestValue(data.getData(TEST_VALUE_COL, line), bad);


                BADGenerator badGenerator = (BADGenerator) ctx.getBean(BADGenerator.class);

                //write
                badGenerator.generateBAD(bad, logLine,templateName);

                //generate test
                BADTestGenerator badTestGenerator = (BADTestGenerator) ctx.getBean(BADTestGenerator.class);

                badTestGenerator.generateBAD(bad, logLine, mapAnswer,templateName);

                bads.add(bad);
            }
        }
        
        //generate main test
        BADTestMainGenerator badTestMainGenerator = new BADTestMainGenerator();
        badTestMainGenerator.generateBAD(bads, templateName);
    }
}

