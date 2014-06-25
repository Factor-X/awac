package uml.reporting;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "indicator")
public class Indicator extends Model {

    private static final long serialVersionUID = 1L;

    public Indicator() {
    }

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}