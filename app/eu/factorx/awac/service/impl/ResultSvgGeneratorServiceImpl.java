package eu.factorx.awac.service.impl;

import java.io.IOException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.service.ResultSvgGeneratorService;
import eu.factorx.awac.service.SvgGenerator;
import eu.factorx.awac.service.impl.reporting.MergedReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.MergedReportResultIndicatorAggregation;
import eu.factorx.awac.service.impl.reporting.ReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.ReportResultIndicatorAggregation;
import eu.factorx.awac.util.Table;

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
		fillTableWithResultDataForHistogram(reportResult, scopeTable, null);
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
		fillTableWithResultDataForHistogram(mergedReportResultAggregation, scopeTable, null);
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

	private void fillTableWithResultDataForHistogram(ReportResultAggregation reportResult, Table scopeTable, IndicatorIsoScopeCode restrictedScopeType) {

		for (ReportResultIndicatorAggregation reportResultIndicatorAggregation : reportResult.getReportResultIndicatorAggregationList()) {

			Double v1 = null;
			Double v2 = null;
			Double v3 = null;
			Double v4 = null;

			if (restrictedScopeType == null) {
				v1 = reportResultIndicatorAggregation.getScope1Value();
				v2 = reportResultIndicatorAggregation.getScope2Value();
				v3 = reportResultIndicatorAggregation.getScope3Value();
				v4 = reportResultIndicatorAggregation.getOutOfScopeValue();
			} else if (IndicatorIsoScopeCode.SCOPE1.equals(restrictedScopeType)) {
				v1 = reportResultIndicatorAggregation.getScope1Value();
			} else if (IndicatorIsoScopeCode.SCOPE2.equals(restrictedScopeType)) {
				v2 = reportResultIndicatorAggregation.getScope2Value();
			} else if (IndicatorIsoScopeCode.SCOPE3.equals(restrictedScopeType)) {
				v3 = reportResultIndicatorAggregation.getScope3Value();
			} else if (IndicatorIsoScopeCode.OUT_OF_SCOPE.equals(restrictedScopeType)) {
				v4 = reportResultIndicatorAggregation.getOutOfScopeValue();
			}

			if ((v1 != null && v1 > 0) || (v2 != null && v2 > 0) || (v3 != null && v3 > 0) || (v4 != null && v4 > 0)) {
				int row = scopeTable.getRowCount();
				scopeTable.setCell(0, row, reportResultIndicatorAggregation.getIndicator());
				scopeTable.setCell(1, row, v1);
				scopeTable.setCell(2, row, v2);
				scopeTable.setCell(3, row, v3);
				scopeTable.setCell(4, row, v4);
			}
		}

	}

	private void fillTableWithResultDataForHistogram(MergedReportResultAggregation mergedReportResultAggregation, Table scopeTable, IndicatorIsoScopeCode restrictedScopeType) {

		for (MergedReportResultIndicatorAggregation mergedReportResultIndicatorAggregation : mergedReportResultAggregation.getMergedReportResultIndicatorAggregationList()) {
			Double left1 = null;
			Double left2 = null;
			Double left3 = null;
			Double left4 = null;
			Double right1 = null;
			Double right2 = null;
			Double right3 = null;
			Double right4 = null;

			if (restrictedScopeType == null) {
				left1 = mergedReportResultIndicatorAggregation.getLeftScope1Value();
				left2 = mergedReportResultIndicatorAggregation.getLeftScope2Value();
				left3 = mergedReportResultIndicatorAggregation.getLeftScope3Value();
				left4 = mergedReportResultIndicatorAggregation.getLeftOutOfScopeValue();

				right1 = mergedReportResultIndicatorAggregation.getRightScope1Value();
				right2 = mergedReportResultIndicatorAggregation.getRightScope2Value();
				right3 = mergedReportResultIndicatorAggregation.getRightScope3Value();
				right4 = mergedReportResultIndicatorAggregation.getRightOutOfScopeValue();
			} else if (IndicatorIsoScopeCode.SCOPE1.equals(restrictedScopeType)) {
				left1 = mergedReportResultIndicatorAggregation.getLeftScope1Value();
				right1 = mergedReportResultIndicatorAggregation.getRightScope1Value();
			} else if (IndicatorIsoScopeCode.SCOPE2.equals(restrictedScopeType)) {
				left2 = mergedReportResultIndicatorAggregation.getLeftScope2Value();
				right2 = mergedReportResultIndicatorAggregation.getRightScope2Value();
			} else if (IndicatorIsoScopeCode.SCOPE3.equals(restrictedScopeType)) {
				left3 = mergedReportResultIndicatorAggregation.getLeftScope3Value();
				right3 = mergedReportResultIndicatorAggregation.getRightScope3Value();
			} else if (IndicatorIsoScopeCode.OUT_OF_SCOPE.equals(restrictedScopeType)) {
				left4 = mergedReportResultIndicatorAggregation.getLeftOutOfScopeValue();
				right4 = mergedReportResultIndicatorAggregation.getRightOutOfScopeValue();
			}

			if ((left1 != null && left1 > 0) || (left2 != null && left2 > 0) || (left3 != null && left3 > 0) || (left4 != null && left4 > 0) || (right1 != null && right1 > 0) || (right2 != null && right2 > 0) || (right3 != null && right3 > 0) || (right4 != null && right4 > 0)) {
				int row = scopeTable.getRowCount();
				scopeTable.setCell(0, row, mergedReportResultIndicatorAggregation.getIndicator());

				scopeTable.setCell(1, row, left1);
				scopeTable.setCell(2, row, left2);
				scopeTable.setCell(3, row, left3);
				scopeTable.setCell(4, row, left4);

				scopeTable.setCell(5, row, right1);
				scopeTable.setCell(6, row, right2);
				scopeTable.setCell(7, row, right3);
				scopeTable.setCell(8, row, right4);
			}
		}


	}


}
