package uml.knowledge;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "factor")
public class Factor extends Model {

    private static final long serialVersionUID = 1L;

    public Factor() {
    }

    @Id
    private long id;
    private String name;
    @ManyToOne
    private Unit unitIn;
    @ManyToOne
    private Unit unitOut;
    @OneToMany(mappedBy = "factor")
    private List<FactorValue> values;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String param) {
        this.name = param;
    }

    public Unit getUnitIn() {
        return unitIn;
    }

    public void setUnitIn(Unit param) {
        this.unitIn = param;
    }

    public Unit getUnitOut() {
        return unitOut;
    }

    public void setUnitOut(Unit param) {
        this.unitOut = param;
    }

    public List<FactorValue> getValues() {
        return values;
    }

    public void setValues(List<FactorValue> param) {
        this.values = param;
    }

}