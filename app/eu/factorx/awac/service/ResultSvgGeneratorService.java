package eu.factorx.awac.service;

import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.impl.reporting.MergedReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.ReportResultAggregation;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.IOException;

public interface ResultSvgGeneratorService {
	String getDonut(ReportResultAggregation reportResult) throws IOException, WriteException, BiffException;

	String getWeb(ReportResultAggregation reportResult) throws IOException, WriteException, BiffException;

	String getHistogram(ReportResultAggregation reportResult) throws IOException, WriteException, BiffException;



	String getWeb(MergedReportResultAggregation mergedReportResultAggregation);

	String getHistogram(MergedReportResultAggregation mergedReportResultAggregation);

	String getLeftDonut(MergedReportResultAggregation mergedReportResultAggregation);

	String getRightDonut(MergedReportResultAggregation mergedReportResultAggregation);
}
