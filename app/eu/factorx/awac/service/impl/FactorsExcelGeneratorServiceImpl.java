package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.FactorValue;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.FactorService;
import eu.factorx.awac.service.FactorsExcelGeneratorService;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

@Component
public class FactorsExcelGeneratorServiceImpl implements FactorsExcelGeneratorService {

    @Autowired
    private FactorService    factorService;
    @Autowired
    private CodeLabelService codeLabelService;

    @Override
    public byte[] generateExcel(LanguageCode lang) throws IOException, WriteException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Create cell font and format
        WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFormat.setAlignment(Alignment.LEFT);
        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale(lang.getKey()));
        WritableWorkbook wb = Workbook.createWorkbook(byteArrayOutputStream, wbSettings);

        WritableSheet sheet = wb.createSheet("Facteurs", 0);
        int row = 0;

        for (Factor factor : factorService.findAll()) {

            sheet.addCell(new Label(0, row, factor.getKey()));

            CodeLabel icLabel = codeLabelService.findCodeLabelByCode(new Code(CodeList.IndicatorCategory, factor.getIndicatorCategory().getKey()));
            sheet.addCell(new Label(1, row, icLabel.getLabel(lang)));

            CodeLabel atLabel = codeLabelService.findCodeLabelByCode(new Code(CodeList.ActivityType, factor.getActivityType().getKey()));
            sheet.addCell(new Label(2, row, atLabel.getLabel(lang)));

            CodeLabel asLabel = codeLabelService.findCodeLabelByCode(new Code(CodeList.ActivitySource, factor.getActivitySource().getKey()));
            sheet.addCell(new Label(3, row, asLabel.getLabel(lang)));

            sheet.addCell(new Label(4, row, factor.getUnitIn().getName()));

            sheet.addCell(new Label(5, row, factor.getInstitution()));

            row++;
            row++;

            for (FactorValue factorValue : factor.getValues()) {
                Integer dateIn = factorValue.getDateIn();
                if (dateIn != null) {
                    sheet.addCell(new Number(1, row, dateIn));
                }

                Integer dateOut = factorValue.getDateOut();
                if (dateOut != null) {
                    sheet.addCell(new Number(2, row, dateOut));
                }
                sheet.addCell(new Number(3, row, factorValue.getValue()));
                sheet.addCell(new Label(4, row, factor.getUnitOut().getSymbol()));
                sheet.addCell(new Label(5, row, "/"));
                sheet.addCell(new Label(6, row, factor.getUnitIn().getSymbol()));

                row++;
            }

            row++;

        }


        wb.write();
        wb.close();

        return byteArrayOutputStream.toByteArray();
    }
}
