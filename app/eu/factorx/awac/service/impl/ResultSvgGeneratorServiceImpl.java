package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.service.ResultSvgGeneratorService;
import eu.factorx.awac.service.SvgGenerator;
import eu.factorx.awac.service.impl.reporting.MergedReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.MergedReportResultIndicatorAggregation;
import eu.factorx.awac.service.impl.reporting.ReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.ReportResultIndicatorAggregation;
import eu.factorx.awac.util.Colors;
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
		return svgGenerator.getDonut(scopeTable, reportResult.getPeriod().getLabel());
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

	// ----------

	@Override
	public String getLeftDonut(MergedReportResultAggregation mergedReportResultAggregation) {
		IndicatorIsoScopeCode reportScope = mergedReportResultAggregation.getReportRestrictedScope();
		Table scopeTable = new Table();
		fillTableWithResultData(mergedReportResultAggregation, scopeTable, reportScope);
		for (int i = 0; i < scopeTable.getRowCount(); i++) {
			scopeTable.setCell(2, i, null);
		}
		return svgGenerator.getDonut(scopeTable, mergedReportResultAggregation.getLeftPeriod().getLabel());
	}

	@Override
	public String getRightDonut(MergedReportResultAggregation mergedReportResultAggregation) {
		IndicatorIsoScopeCode reportScope = mergedReportResultAggregation.getReportRestrictedScope();
		Table scopeTable = new Table();
		fillTableWithResultData(mergedReportResultAggregation, scopeTable, reportScope);
		for (int i = 0; i < scopeTable.getRowCount(); i++) {
			scopeTable.setCell(1, i, scopeTable.getCell(2, i));
			scopeTable.setCell(2, i, null);
		}
		return svgGenerator.getDonut(scopeTable, mergedReportResultAggregation.getRightPeriod().getLabel());
	}

	@Override
	public String getWeb(MergedReportResultAggregation mergedReportResultAggregation) {
		Table scopeTable = new Table();
		fillTableWithResultData(mergedReportResultAggregation, scopeTable, null);
		return svgGenerator.getWeb(scopeTable);
	}

	@Override
	public String getHistogram(MergedReportResultAggregation mergedReportResultAggregation) {
		Table scopeTable = new Table();
		fillTableWithResultData(mergedReportResultAggregation, scopeTable, null);
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

	private void fillTableWithResultData(MergedReportResultAggregation mergedReportResultAggregation, Table scopeTable, IndicatorIsoScopeCode restrictedScopeType) {

		for (MergedReportResultIndicatorAggregation mergedReportResultIndicatorAggregation : mergedReportResultAggregation.getMergedReportResultIndicatorAggregationList()) {
			double left = 0;
			double right = 0;

			if (restrictedScopeType == null) {
				left = mergedReportResultIndicatorAggregation.getLeftTotalValue();
				right = mergedReportResultIndicatorAggregation.getRightTotalValue();
			} else if (IndicatorIsoScopeCode.SCOPE1.equals(restrictedScopeType)) {
				left = mergedReportResultIndicatorAggregation.getLeftScope1Value();
				right = mergedReportResultIndicatorAggregation.getRightScope1Value();
			} else if (IndicatorIsoScopeCode.SCOPE2.equals(restrictedScopeType)) {
				left = mergedReportResultIndicatorAggregation.getLeftScope2Value();
				right = mergedReportResultIndicatorAggregation.getRightScope2Value();
			} else if (IndicatorIsoScopeCode.SCOPE3.equals(restrictedScopeType)) {
				left = mergedReportResultIndicatorAggregation.getLeftScope3Value();
				right = mergedReportResultIndicatorAggregation.getRightScope3Value();
			} else if (IndicatorIsoScopeCode.OUT_OF_SCOPE.equals(restrictedScopeType)) {
				left = mergedReportResultIndicatorAggregation.getLeftOutOfScopeValue();
				right = mergedReportResultIndicatorAggregation.getRightOutOfScopeValue();
			}

			if (left + right > 0) {
				int row = scopeTable.getRowCount();
				scopeTable.setCell(0, row, mergedReportResultIndicatorAggregation.getIndicator());
				scopeTable.setCell(1, row, left);
				scopeTable.setCell(2, row, right);
			}
		}


	}


}
