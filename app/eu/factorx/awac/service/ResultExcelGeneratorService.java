package eu.factorx.awac.service;

import eu.factorx.awac.util.Table;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.IOException;
import java.io.OutputStream;

public interface ResultExcelGeneratorService {

	void generateExcelInStream(OutputStream stream, String site, String period, Table allScopes, Table scope1, Table scope2, Table scope3, Table outOfScope)
		throws IOException, WriteException, BiffException;
}
