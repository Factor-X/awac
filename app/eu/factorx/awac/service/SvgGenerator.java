package eu.factorx.awac.service;

import eu.factorx.awac.util.Table;

public interface SvgGenerator {
	String getDonut(Table data, String period);

	String getSimpleDonut(Table data, String period);

	String getWeb(Table data);

	String getHistogram(Table data);

	String getSimpleHistogram(Table data);
}
