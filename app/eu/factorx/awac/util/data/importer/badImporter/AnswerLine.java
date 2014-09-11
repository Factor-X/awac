package eu.factorx.awac.util.data.importer.badImporter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by florian on 11/09/14.
 */
public class AnswerLine {

    private Object value;

    private Map<String,Integer> mapRepetition = new HashMap<>();

    public AnswerLine(Object value) {
        this.value = value;
    }

    public AnswerLine(Object value, Map<String, Integer> mapRepetition) {
        this.value = value;
        this.mapRepetition = mapRepetition;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
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
        return "AnswerLine{" +
                "value=" + value +
                ", mapRepetition=" + mapRepetition +
                '}';
    }
}
