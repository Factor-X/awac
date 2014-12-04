package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDTO<T> extends DTO {

    private Map<String, T> map;

    public MapDTO() {
        map = new HashMap<>();
    }

    @Override
    public String toString() {
        return "MapDTO{" +
            "map=" + map +
            '}';
    }

    public Map<String, T> getMap() {
        return map;
    }

    public void setMap(Map<String, T> map) {
        this.map = map;
    }


}
