package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.service.QuestionService;
import eu.factorx.awac.util.MyrmexException;
import eu.factorx.awac.util.data.importer.ExcelEquivalenceColumn;
import eu.factorx.awac.util.data.importer.WorkbookDataImporter;
import eu.factorx.awac.util.data.importer.badImporter.Reader.Data;
import eu.factorx.awac.util.data.importer.badImporter.Reader.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 29/08/14.
 * <p/>
 * !! the column F of the excel file is used to detect a bad : if the column is not empty, it's a BAD !!
 */
@Component
public class BADImporter extends WorkbookDataImporter {

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


    private List<BAD> listBAD = new ArrayList<>();

    private BADLog badLog = new BADLog();

    @Autowired
    private QuestionService questionService;

    @Autowired
    private BADControlElement badControlElement;



    public void run() {
        try {
            importData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final String ENTERPRISE_METHOD = "site entreprise-activityData";

    @Override
    protected void importData() throws Exception {

        play.Logger.info("run badimporter....");


        ExcelReader excelReader = new ExcelReader();

        Data data = excelReader.readFile(FILE_PATH, ENTERPRISE_METHOD);

        //2. read
        reader(data);

        play.Logger.info("run badimporter end !");

    }


    //not null, ActivityType or answer(control list content) or more complex

    public void reader(Data data) {

        badControlElement.setBadLog(badLog);
        BADGenerator badGenerator = new BADGenerator();

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
                badLog.addToLog(BADLog.LogType.INFO, line, "This is a BAD " + data.getData(ExcelEquivalenceColumn.B, line));


                //create and write new BAD
                boolean toGenerate = true;


                // --- test badKey ---
                String badKey = null;
                try {
                    badKey = badControlElement.controlBADKey(data.getData(BAD_KEY_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }

                // ---- name ----
                String name = null;
                try {
                    name = badControlElement.controlName(data.getData(BAD_NAME_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }

                // ---- control rank ---
                Integer rank = null;
                try {
                    rank = badControlElement.controlRank(data.getData(BAD_RANK_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }

                // ---- specific purpose ----
                String specificPurpose = null;
                try {
                    specificPurpose = badControlElement.controlSpecificPurpose(data.getData(BAD_SPECIFIC_PURPOSE_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }

                // ---- activityCategory ---
                String activityCategory = null;
                try {
                    activityCategory = badControlElement.controlActivityCategory(data.getData(BAD_ACTIVITY_CATEGORY_KEY_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }

                // --- activitySubCategory ---
                String activitySubCategory = null;
                try {
                    activitySubCategory = badControlElement.controlActivitySubCategory(data.getData(BAD_ACTIVITY_SUB_CATEGORY_KEY_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }

                // --- ActivityType ---
                String activityType = null;
                try {
                    activityType = badControlElement.controlActivityType(data.getData(BAD_ACTIVITY_TYPE_KEY_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }

                // --- ActivitySource ---
                String activitySource = null;
                try {
                    activitySource = badControlElement.controlActivitySource(data.getData(BAD_ACTIVITY_SOURCE_KEY_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }

                // --- activityOwnerShip ---
                String activityOwnerShip = null;
                try {
                    activityOwnerShip = badControlElement.controlActivityOwnerShip(data.getData(BAD_ACTIVITY_OWNERSHIP_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }

                // ---- unit ---
                String unit = null;
                try {
                    unit = badControlElement.controlUnit(data.getData(BAD_UNIT_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }

                // --- value ---
                String value = null;
                try {
                    value = badControlElement.controlValue(data.getData(BAD_VALUE_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }

                // ----- condition ---
                String condition = null;
                try {
                    condition = badControlElement.controlCondition(data.getData(BAD_CONDITION_COL, line), line);
                } catch (MyrmexException e) {
                    toGenerate = false;
                }


                //create BAD
                if (toGenerate) {
                    BAD bad = new BAD();
                    bad.setBaseActivityDataCode(badKey);
                    bad.setName(name);
                    bad.setRank(rank);
                    bad.setSpecificPurpose(specificPurpose);
                    bad.setActivityCategoryCode(activityCategory);
                    bad.setActivitySubCategory(activitySubCategory);
                    bad.setActivityType(activityType);
                    bad.setActivitySource(activitySource);
                    bad.setActivityOwnership(activityOwnerShip);
                    bad.setUnit(unit);
                    bad.setValue(value);
                    bad.setCondition(condition);

                    //print
                    //Logger.info(bad.toString());

                    listBAD.add(bad);

                    //write
                    badGenerator.generateBAD(bad);
                }
            }
        }
    }
}
