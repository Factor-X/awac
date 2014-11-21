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
import jxl.Range;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.CellFormat;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
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

        sheet.addCell(new Label(0, row, "Clé", cellFormat));
        sheet.addCell(new Label(1, row, "Catégorie", cellFormat));
        sheet.addCell(new Label(4, row, "Unité entrante", cellFormat));
        sheet.addCell(new Label(5, row, "Origine", cellFormat));


        row++;

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

            List<FactorValue> values = factor.getValues();

            if (values.size() > 0) {

                row++;

                int begin = row;

                sheet.addCell(new Label(1, row, "De", cellFormat));
                sheet.addCell(new Label(2, row, "à", cellFormat));
                sheet.addCell(new Label(3, row, "Valeur", cellFormat));
                sheet.addCell(new Label(4, row, "Unité sortante", cellFormat));
                sheet.addCell(new Label(5, row, "", cellFormat));
                sheet.addCell(new Label(6, row, "Unité entrante", cellFormat));

                row++;


                for (FactorValue factorValue : values) {
                    Integer dateIn = factorValue.getDateIn();
                    if (dateIn != null) {
                        sheet.addCell(new Number(1, row, dateIn));
                    } else {
                        sheet.addCell(new Label(1, row, ""));
                    }

                    Integer dateOut = factorValue.getDateOut();
                    if (dateOut != null) {
                        sheet.addCell(new Number(2, row, dateOut));
                    } else {
                        sheet.addCell(new Label(2, row, ""));
                    }
                    sheet.addCell(new Number(3, row, factorValue.getValue()));
                    sheet.addCell(new Label(4, row, factor.getUnitOut().getSymbol()));
                    sheet.addCell(new Label(5, row, "/"));
                    sheet.addCell(new Label(6, row, factor.getUnitIn().getSymbol()));

                    row++;
                }

                sheet.addCell(new Label(1, row, ""));
                sheet.addCell(new Label(2, row, ""));
                sheet.addCell(new Label(3, row, ""));
                sheet.addCell(new Label(4, row, ""));
                sheet.addCell(new Label(5, row, ""));
                sheet.addCell(new Label(6, row, ""));

                drawBorder(sheet, 1, begin, 6, row - 1);

                row++;
            }

        }


        wb.write();
        wb.close();

        return byteArrayOutputStream.toByteArray();
    }

    private WritableCell getRealCell(WritableSheet sheet, int col, int row) {
        for (Range r : sheet.getMergedCells()) {
            if (col >= r.getTopLeft().getColumn()
                && col <= r.getBottomRight().getColumn()
                && row <= r.getTopLeft().getRow()
                && row >= r.getBottomRight().getRow()) {
                return (WritableCell) r.getTopLeft();
            }
        }
        return sheet.getWritableCell(col, row);
    }

    private void drawBorder(WritableSheet sheet, int col1, int row1, int col2, int row2) throws WriteException {

        jxl.format.BorderLineStyle borderWidth = jxl.format.BorderLineStyle.THIN;

        for (int i = row1; i <= row2; i++) {
            WritableCell writableCell = getRealCell(sheet, col1, i);
            CellFormat format = writableCell.getCellFormat();
            WritableCellFormat cellFormat;
            if (format == null) {
                cellFormat = new WritableCellFormat();
            } else {
                cellFormat = new WritableCellFormat(format);
            }
            cellFormat.setBorder(jxl.format.Border.LEFT, borderWidth);
            writableCell.setCellFormat(cellFormat);
        }

        for (int i = row1; i <= row2; i++) {
            WritableCell writableCell = getRealCell(sheet, col2, i);
            CellFormat format = writableCell.getCellFormat();
            WritableCellFormat cellFormat;
            if (format == null) {
                cellFormat = new WritableCellFormat();
            } else {
                cellFormat = new WritableCellFormat(format);
            }
            cellFormat.setBorder(jxl.format.Border.RIGHT, borderWidth);
            writableCell.setCellFormat(cellFormat);
        }

        for (int i = col1; i <= col2; i++) {
            WritableCell writableCell = getRealCell(sheet, i, row1);
            CellFormat format = writableCell.getCellFormat();
            WritableCellFormat cellFormat;
            if (format == null) {
                cellFormat = new WritableCellFormat();
            } else {
                cellFormat = new WritableCellFormat(format);
            }
            cellFormat.setBorder(jxl.format.Border.TOP, borderWidth);
            writableCell.setCellFormat(cellFormat);
        }

        for (int i = col1; i <= col2; i++) {
            WritableCell writableCell = getRealCell(sheet, i, row2);
            CellFormat format = writableCell.getCellFormat();
            WritableCellFormat cellFormat;
            if (format == null) {
                cellFormat = new WritableCellFormat();
            } else {
                cellFormat = new WritableCellFormat(format);
            }
            cellFormat.setBorder(jxl.format.Border.BOTTOM, borderWidth);
            writableCell.setCellFormat(cellFormat);
        }


    }
}
