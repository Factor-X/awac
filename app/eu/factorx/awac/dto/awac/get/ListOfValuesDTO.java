package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;

import eu.factorx.awac.dto.awac.shared.ListOfValuesLine;

public class ListOfValuesDTO extends ArrayList<ListOfValuesLine> {

	public void add(Object key, Object value) {
		this.add(new ListOfValuesLine(key, value));
	}
}

