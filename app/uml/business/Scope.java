package uml.business;

import play.db.ebean.Model;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name = "scope_type", discriminatorType = DiscriminatorType.INTEGER)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "scope")
public abstract class Scope extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    protected long id;
    protected String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}