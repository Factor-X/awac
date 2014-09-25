package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.service.ResultSvgGeneratorService;
import eu.factorx.awac.service.SvgGenerator;
import eu.factorx.awac.service.impl.reporting.ReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.ReportResultIndicatorAggregation;
import eu.factorx.awac.util.Table;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResultSvgGeneratorServiceImpl implements ResultSvgGeneratorService {

	@Autowired
	private SvgGenerator svgGenerator;

	//
	// Charts
	//

	@Override
	public String getDonut(ReportResultAggregation reportResult) throws IOException, WriteException, BiffException {
		IndicatorIsoScopeCode reportScope = reportResult.getReportRestrictedScope();
		Table scopeTable = new Table();
		fillTableWithResultData(reportResult, scopeTable, reportScope);
		return svgGenerator.getDonut(scopeTable);
	}


	@Override
	public String getWeb(ReportResultAggregation reportResult) throws IOException, WriteException, BiffException {
		IndicatorIsoScopeCode reportScope = reportResult.getReportRestrictedScope();
		Table scopeTable = new Table();
		fillTableWithResultData(reportResult, scopeTable, null);
		return svgGenerator.getWeb(scopeTable);
	}


	@Override
	public String getHistogram(ReportResultAggregation reportResult) throws IOException, WriteException, BiffException {
		Table scopeTable = new Table();
		fillTableWithResultData(reportResult, scopeTable, null);
		return svgGenerator.getHistogram(scopeTable);
	}

	//
	// Util
	//

	private void fillTableWithResultData(ReportResultAggregation reportResult, Table scopeTable, IndicatorIsoScopeCode restrictedScopeType) {

		for (ReportResultIndicatorAggregation reportResultIndicatorAggregation : reportResult.getReportResultIndicatorAggregationList()) {

			double v = 0;

			if (restrictedScopeType == null) {
				v = reportResultIndicatorAggregation.getTotalValue();
			} else if (IndicatorIsoScopeCode.SCOPE1.equals(restrictedScopeType)) {
				v = reportResultIndicatorAggregation.getScope1Value();
			} else if (IndicatorIsoScopeCode.SCOPE2.equals(restrictedScopeType)) {
				v = reportResultIndicatorAggregation.getScope2Value();
			} else if (IndicatorIsoScopeCode.SCOPE3.equals(restrictedScopeType)) {
				v = reportResultIndicatorAggregation.getScope3Value();
			} else if (IndicatorIsoScopeCode.OUT_OF_SCOPE.equals(restrictedScopeType)) {
				v = reportResultIndicatorAggregation.getOutOfScopeValue();
			}

			if (v > 0) {
				int row = scopeTable.getRowCount();
				scopeTable.setCell(0, row, reportResultIndicatorAggregation.getIndicator());
				scopeTable.setCell(1, row, v);
			}
		}

	}


}
