package eu.factorx.awac.service.impl.reporting;

import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.models.reporting.BaseActivityData;

public class NoSuitableFactor extends ReportLogEntry {

	private final BaseIndicator    baseIndicator;
	private final BaseActivityData bad;

	public NoSuitableFactor(BaseIndicator baseIndicator, BaseActivityData bad) {

		this.baseIndicator = baseIndicator;
		this.bad = bad;
	}

	public BaseIndicator getBaseIndicator() {
		return baseIndicator;
	}

	public BaseActivityData getBad() {
		return bad;
	}
}
