package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.awac.shared.ListOfValuesLine;

import java.util.ArrayList;

public class ListOfValuesDTO extends ArrayList<ListOfValuesLine> {

    public void add(Object key, Object value) {
        this.add(new ListOfValuesLine(key, value));
    }
}

