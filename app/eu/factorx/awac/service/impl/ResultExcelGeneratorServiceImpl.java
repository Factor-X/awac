package eu.factorx.awac.service.impl;

import eu.factorx.awac.service.ResultExcelGeneratorService;
import eu.factorx.awac.util.Table;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import jxl.write.Number;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class ResultExcelGeneratorServiceImpl implements ResultExcelGeneratorService {

	@Override
	public void generateExcelInStream(OutputStream stream, Table allScopes, Table scope1, Table scope2, Table scope3, Table outOfScope) throws IOException, WriteException {

		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("Cp1252");
		WritableWorkbook workbook = Workbook.createWorkbook(stream, ws);

		WritableSheet sheetAllScopes = workbook.createSheet("Tous les scopes", 0);
		WritableSheet sheetScope1 = workbook.createSheet("Scope 1", 1);
		WritableSheet sheetScope2 = workbook.createSheet("Scope 2", 2);
		WritableSheet sheetScope3 = workbook.createSheet("Scope 3", 3);
		WritableSheet sheetOutOfScope = workbook.createSheet("Hors scope", 4);

		writeSheet(sheetAllScopes, 0, 3, allScopes);
		writeSheet(sheetScope1, 0, 3, scope1);
		writeSheet(sheetScope2, 0, 3, scope2);
		writeSheet(sheetScope3, 0, 3, scope3);
		writeSheet(sheetOutOfScope, 0, 3, outOfScope);

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
