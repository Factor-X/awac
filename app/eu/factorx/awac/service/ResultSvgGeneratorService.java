package eu.factorx.awac.service;

import java.io.IOException;
import java.util.Map;

import eu.factorx.awac.models.forms.AwacCalculator;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import eu.factorx.awac.service.impl.reporting.MergedReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.ReportResultAggregation;

public interface ResultSvgGeneratorService {
	String getDonut(AwacCalculator awacCalculator, ReportResultAggregation reportResult) throws IOException, WriteException, BiffException;
	String getWeb(AwacCalculator awacCalculator, ReportResultAggregation reportResult) throws IOException, WriteException, BiffException;
	String getHistogram(AwacCalculator awacCalculator, ReportResultAggregation reportResult) throws IOException, WriteException, BiffException;
	String getWebWithReferences(AwacCalculator awacCalculator, ReportResultAggregation reportResult, Map<String, Double> type, Map<String, Double> ideal) ;

	String getWeb(AwacCalculator awacCalculator, MergedReportResultAggregation mergedReportResultAggregation);
	String getHistogram(AwacCalculator awacCalculator, MergedReportResultAggregation mergedReportResultAggregation);
	String getLeftDonut(AwacCalculator awacCalculator, MergedReportResultAggregation mergedReportResultAggregation);
	String getRightDonut(AwacCalculator awacCalculator, MergedReportResultAggregation mergedReportResultAggregation);
	String getWebWithReferences(AwacCalculator awacCalculator, MergedReportResultAggregation mergedReportResultAggregation, Map<String, Double> type, Map<String, Double> ideal);
}
