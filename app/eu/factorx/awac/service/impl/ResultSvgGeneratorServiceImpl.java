package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.ResultSvgGeneratorService;
import eu.factorx.awac.service.SvgGenerator;
import eu.factorx.awac.util.Table;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ResultSvgGeneratorServiceImpl implements ResultSvgGeneratorService {

	@Autowired
	private SvgGenerator svgGenerator;

	//
	// Charts
	//

	@Override
	public String getDonut(ReportResult reportResult) throws IOException, WriteException, BiffException {
		IndicatorIsoScopeCode reportScope = reportResult.getReport().getRestrictedScope();
		Table scopeTable = new Table();

		int scopeTypeIndex = 0;
		if (IndicatorIsoScopeCode.SCOPE1.equals(reportScope)) scopeTypeIndex = 1;
		if (IndicatorIsoScopeCode.SCOPE2.equals(reportScope)) scopeTypeIndex = 2;
		if (IndicatorIsoScopeCode.SCOPE3.equals(reportScope)) scopeTypeIndex = 3;
		if (IndicatorIsoScopeCode.OUT_OF_SCOPE.equals(reportScope)) scopeTypeIndex = 4;

		fillTableWithResultData(reportResult, scopeTable, scopeTypeIndex);

		return svgGenerator.getDonut(scopeTable);
	}


	@Override
	public String getWeb(ReportResult reportResult) throws IOException, WriteException, BiffException {
		IndicatorIsoScopeCode reportScope = reportResult.getReport().getRestrictedScope();
		Table scopeTable = new Table();
		int scopeTypeIndex = 0;

		fillTableWithResultData(reportResult, scopeTable, scopeTypeIndex);

		return svgGenerator.getDonut(scopeTable);
	}


	@Override
	public String getHistogram(ReportResult reportResult) throws IOException, WriteException, BiffException {
		IndicatorIsoScopeCode reportScope = reportResult.getReport().getRestrictedScope();
		Table scopeTable = new Table();
		int scopeTypeIndex = 0;

		fillTableWithResultData(reportResult, scopeTable, scopeTypeIndex);

		return svgGenerator.getDonut(scopeTable);
	}

	//
	// Util
	//

	private void fillTableWithResultData(ReportResult reportResult, Table scopeTable, int scopeTypeIndex) {
		for (Map.Entry<String, List<Double>> entry : reportResult.getScopeValuesByIndicator().entrySet()) {
			double v = entry.getValue().get(scopeTypeIndex);
			if (v > 0) {
				int row = scopeTable.getRowCount();
				scopeTable.setCell(0, row, entry.getKey());
				scopeTable.setCell(1, row, v);
			}
		}
	}


}
