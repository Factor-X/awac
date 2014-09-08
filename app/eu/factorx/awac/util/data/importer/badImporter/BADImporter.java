package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.service.QuestionService;
import eu.factorx.awac.util.MyrmexException;
import eu.factorx.awac.util.data.importer.ExcelEquivalenceColumn;
import eu.factorx.awac.util.data.importer.WorkbookDataImporter;
import eu.factorx.awac.util.data.importer.badImporter.Reader.Data;
import eu.factorx.awac.util.data.importer.badImporter.Reader.ExcelReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 29/08/14.
 * <p/>
 * !! the column F of the excel file is used to detect a bad : if the column is not empty, it's a BAD !!
 */
@Component
public class BADImporter extends WorkbookDataImporter implements ApplicationContextAware {

    private final static String FILE_PATH = "data_importer_resources/awac_data_09-08-2014/AWAC-tous-calcul_FE.xls";

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



    public BADLog importBAD() {
        BADLog badLog = new BADLog();
        try {

            importDatas(badLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return badLog;
    }

    public static final String ENTERPRISE_METHOD = "site entreprise-activityData";

    @Override
    protected void importData() throws Exception {
        //useless
    }

    protected void importDatas(BADLog badLog) throws Exception {

        play.Logger.info("run badimporter....");


        ExcelReader excelReader = new ExcelReader();

        Data data = excelReader.readFile(FILE_PATH, ENTERPRISE_METHOD);

        //2. read
        reader(data,badLog);

        play.Logger.info("run badimporter end !");

    }


    //not null, ActivityType or answer(control list content) or more complex

    public void reader(Data data,BADLog badLog) {

        badControlElement.setBadLog(badLog);


        for (int line = 1; line < data.getNbRows(); line++) {

            //escape the first line : presentation
            if (line == 1) {
                continue;
            }

            //F is the reference column => not empty = it's a BAD !
            if (data.getData(ExcelEquivalenceColumn.B, line) != null &&
                    !data.getData(ExcelEquivalenceColumn.B, line).equals("BAD-KEY") &&
                    data.getData(ExcelEquivalenceColumn.B, line).contains("BAD")) {
                // activity data founded

                //print the line in DEBUG
                badLog.addToLog(BADLog.LogType.ID, line, data.getData(BAD_KEY_COL, line)+"("+data.getData(BAD_NAME_COL, line)+")");


                //create the bad
                BAD bad = new BAD();


                // --- test badKey ---
                badControlElement.controlBADKey(data.getData(BAD_KEY_COL, line), line, bad);

                // ---- name ----
                badControlElement.controlName(data.getData(BAD_NAME_COL, line), line, bad);

                // ---- control rank ---
                badControlElement.controlRank(data.getData(BAD_RANK_COL, line), line, bad);

                // ---- unit ---
                badControlElement.controlUnit(data.getData(BAD_UNIT_COL, line), line, bad);

                // ---- specific purpose ----
                badControlElement.controlSpecificPurpose(data.getData(BAD_SPECIFIC_PURPOSE_COL, line), line, bad);

                // ---- activityCategory ---
                badControlElement.controlActivityCategory(data.getData(BAD_ACTIVITY_CATEGORY_KEY_COL, line), line, bad);

                // --- activitySubCategory ---
                badControlElement.controlActivitySubCategory(data.getData(BAD_ACTIVITY_SUB_CATEGORY_KEY_COL, line), line, bad);

                // --- ActivityType ---
                badControlElement.controlActivityType(data.getData(BAD_ACTIVITY_TYPE_KEY_COL, line), line, bad);

                // --- ActivitySource ---
                badControlElement.controlActivitySource(data.getData(BAD_ACTIVITY_SOURCE_KEY_COL, line), line, bad);

                // --- activityOwnerShip ---
                badControlElement.controlActivityOwnerShip(data.getData(BAD_ACTIVITY_OWNERSHIP_COL, line), line, bad);

                // --- value ---
                badControlElement.controlValue(data.getData(BAD_VALUE_COL, line), line, bad);

                // ----- condition ---
                badControlElement.controlCondition(data.getData(BAD_CONDITION_COL, line), line, bad);


                //create BAD
                //if (bad.isCanBeGenerated()) {



                BADGenerator badGenerator = (BADGenerator) ctx.getBean(BADGenerator.class);

                    //write
                    badGenerator.generateBAD(bad);
                //}
            }
        }
    }
}
