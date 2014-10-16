package eu.factorx.awac.service.impl.reporting;

import eu.factorx.awac.models.reporting.BaseActivityData;

public class LowerRankInGroup extends ReportLogEntry {
	private BaseActivityData baseActivityData;

	public LowerRankInGroup(BaseActivityData baseActivityData) {

		this.baseActivityData = baseActivityData;
	}

	public BaseActivityData getBaseActivityData() {
		return baseActivityData;
	}

	public void setBaseActivityData(BaseActivityData baseActivityData) {
		this.baseActivityData = baseActivityData;
	}
}
