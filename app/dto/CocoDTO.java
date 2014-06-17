package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CocoDTO implements Serializable {
    private List<String> names;

    public CocoDTO() {
        names = new ArrayList<>();
        names.add("Hector");
        names.add("Fancine");
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
