package uml.knowledge;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "period")
public class Period extends Model {

    private static final long serialVersionUID = 1L;

    public Period() {
    }

    @Id
    private long id;
    private String label;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String param) {
        this.label = param;
    }

}