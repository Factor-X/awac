package eu.factorx.awac.models.data.question;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.knowledge.Period;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "driver")
public class Driver extends AuditedAbstractEntity {

    private String name;

    @OneToMany( mappedBy = "driver")
    private List<DriverValue> driverValueList;

    public Driver() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DriverValue> getDriverValueList() {
        return driverValueList;
    }

    public void setDriverValueList(List<DriverValue> driverValueList) {
        this.driverValueList = driverValueList;
    }

    @Override
    public String toString() {
        return "Driver{}";
    }
}
