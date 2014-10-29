package eu.factorx.awac.util.data.importer.badImporter.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import eu.factorx.awac.util.data.importer.WorkbookDataImporter;

/**
 * Created by florian on 1/09/14.
 */
public class ExcelReader extends WorkbookDataImporter {

	public Data readFile(String filePath,String sheetName){

		List<DataCell> result = new ArrayList<>();


		Map<String, Sheet> awacDataWbSheets = getWorkbookSheets(filePath);//AwacDataImporter.AWAC_DATA_WORKBOOK_PATH);

		Sheet sheet = awacDataWbSheets.get(sheetName);


		for(int i=0;i<sheet.getColumns();i++){
			for(int j=0;j<sheet.getRows();j++){
				if(getCellStringContent(sheet, i, j)!=null) {
					result.add(new DataCell(i, j, getCellStringContent(sheet, i, j)));
				}
			}
		}

		return new Data(result);
	}

	@Override
	protected void importData() throws Exception {
		//useless
	}


}
