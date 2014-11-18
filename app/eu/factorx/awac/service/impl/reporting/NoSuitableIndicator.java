package eu.factorx.awac.service.impl.reporting;

import eu.factorx.awac.models.reporting.BaseActivityData;

public class NoSuitableIndicator extends ReportLogEntry {

	private BaseActivityData baseActivityData;

	public NoSuitableIndicator(BaseActivityData bad) {

		this.baseActivityData = bad;
	}

	public BaseActivityData getBaseActivityData() {
		return baseActivityData;
	}

	public void setBaseActivityData(BaseActivityData baseActivityData) {
		this.baseActivityData = baseActivityData;
	}
}
