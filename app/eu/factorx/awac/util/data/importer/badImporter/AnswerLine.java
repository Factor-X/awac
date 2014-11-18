package eu.factorx.awac.util.data.importer.badImporter;

import java.util.HashMap;
import java.util.Map;

import eu.factorx.awac.models.knowledge.Unit;

/**
 * Created by florian on 11/09/14.
 */
public class AnswerLine {

    private Object value;

    private Map<String,Integer> mapRepetition = new HashMap<>();

    private Unit unit;

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
                ", unit=" + unit +
                '}';
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }
}
