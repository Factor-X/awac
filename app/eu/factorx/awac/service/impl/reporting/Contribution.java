package eu.factorx.awac.service.impl.reporting;

import eu.factorx.awac.models.reporting.BaseActivityResult;

public class Contribution extends ReportLogEntry {
	private BaseActivityResult bar;

	public Contribution(BaseActivityResult bar) {

		this.bar = bar;
	}

	public BaseActivityResult getBar() {
		return bar;
	}
}
