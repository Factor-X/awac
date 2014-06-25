package uml.forms;

import play.db.ebean.Model;
import uml.knowledge.Period;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "campaign")
public class Campaign extends Model {

    private static final long serialVersionUID = 1L;

    public Campaign() {
    }

    @Id
    private long id;
    @OneToMany(mappedBy = "campaign")
    private List<Form> forms;
    @ManyToOne(optional = false)
    private Period period;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> param) {
        this.forms = param;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period param) {
        this.period = param;
    }

}