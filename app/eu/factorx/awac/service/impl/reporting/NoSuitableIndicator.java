package eu.factorx.awac.service.impl.reporting;

import eu.factorx.awac.service.impl.IndicatorSearchParameter;

public class NoSuitableIndicator extends ReportLogEntry {
	private String                   key;
	private IndicatorSearchParameter indicatorSearchParam;

	public NoSuitableIndicator(String key, IndicatorSearchParameter indicatorSearchParam) {

		this.key = key;
		this.indicatorSearchParam = indicatorSearchParam;
	}

	public String getKey() {
		return key;
	}

	public IndicatorSearchParameter getIndicatorSearchParam() {
		return indicatorSearchParam;
	}
}
