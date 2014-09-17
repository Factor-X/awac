package eu.factorx.awac.service;

import eu.factorx.awac.models.reporting.ReportResult;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.IOException;

public interface ResultSvgGeneratorService {
	String getDonut(ReportResult reportResult) throws IOException, WriteException, BiffException;

	String getWeb(ReportResult reportResult) throws IOException, WriteException, BiffException;

	String getHistogram(ReportResult reportResult) throws IOException, WriteException, BiffException;
}
