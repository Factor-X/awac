package eu.factorx.awac.service;

import eu.factorx.awac.util.Table;

public interface SvgGenerator {
	public String getDonut(Table data);

	public String getWeb(Table data);

	public String getHistogram(Table data);
}
