package eu.factorx.awac.util.data.importer.badImporter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by florian on 12/09/14.
 */
public class BadTestValue {

    private Double value;

    private Map<String,Integer> mapRepetition = new HashMap<>();

    public BadTestValue(Double value) {
        this.value = value;
    }

    public BadTestValue(Map<String, Integer> mapRepetition) {
        this.mapRepetition = mapRepetition;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Map<String, Integer> getMapRepetition() {
        return mapRepetition;
    }

    public void setMapRepetition(Map<String, Integer> mapRepetition) {
        this.mapRepetition = mapRepetition;
    }

    @Override
    public String toString() {
        return "BadTestValue{" +
                "value=" + value +
                ", mapRepetition=" + mapRepetition +
                '}';
    }
}
