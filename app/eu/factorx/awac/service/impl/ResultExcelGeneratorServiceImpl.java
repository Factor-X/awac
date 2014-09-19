package eu.factorx.awac.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;

import org.springframework.stereotype.Component;

import play.Play;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.service.ResultExcelGeneratorService;
import eu.factorx.awac.util.Table;

@Component
public class ResultExcelGeneratorServiceImpl implements ResultExcelGeneratorService {

	@Override
	public void generateExcelInStream(OutputStream stream, List<Scope> scopes, String period, Table allScopes, Table scope1, Table scope2, Table scope3, Table outOfScope)
		throws IOException, WriteException, BiffException {

		Workbook template = Workbook.getWorkbook(new File(Play.application().path() + "/templates/results_template.xls"));

		WritableWorkbook workbook = Workbook.createWorkbook(stream, template);

		WritableSheet sheetAllScopes = workbook.getSheet(0);
		WritableSheet sheetScope1 = workbook.getSheet(1);
		WritableSheet sheetScope2 = workbook.getSheet(2);
		WritableSheet sheetScope3 = workbook.getSheet(3);
		WritableSheet sheetOutOfScope = workbook.getSheet(4);

		writeSheet(sheetAllScopes, 0, 5, allScopes);
		writeSheet(sheetScope1, 0, 5, scope1);
		writeSheet(sheetScope2, 0, 5, scope2);
		writeSheet(sheetScope3, 0, 5, scope3);
		writeSheet(sheetOutOfScope, 0, 5, outOfScope);

//		sheetAllScopes.addCell(new Label(0, 2, site));
//		sheetAllScopes.addCell(new Label(1, 2, period));
//
//		sheetScope1.addCell(new Label(0, 2, site));
//		sheetScope1.addCell(new Label(1, 2, period));
//
//		sheetScope2.addCell(new Label(0, 2, site));
//		sheetScope2.addCell(new Label(1, 2, period));
//
//		sheetScope3.addCell(new Label(0, 2, site));
//		sheetScope3.addCell(new Label(1, 2, period));
//
//		sheetOutOfScope.addCell(new Label(0, 2, site));
//		sheetOutOfScope.addCell(new Label(1, 2, period));

		workbook.write();
		workbook.close();
	}

	private void writeSheet(WritableSheet sheet, int firstColumn, int firstRow, Table table) throws WriteException {

		for (int r = 0; r < table.getRowCount(); r++) {
			for (int c = 0; c < table.getColumnCount(); c++) {
				Object value = table.getCell(c, r);
				if (value != null) {
					if (value instanceof Integer) {
						sheet.addCell(new Number(firstColumn + c, firstRow + r, (Integer) value));
					} else if (value instanceof Double) {
						sheet.addCell(new Number(firstColumn + c, firstRow + r, (Double) value));
					} else if (value instanceof Float) {
						sheet.addCell(new Number(firstColumn + c, firstRow + r, (Float) value));
					} else if (value instanceof Long) {
						sheet.addCell(new Number(firstColumn + c, firstRow + r, (Long) value));
					} else {
						sheet.addCell(new Label(firstColumn + c, firstRow + r, value.toString()));
					}
				}
			}
		}


	}


}
