package eu.factorx.awac.models.data.question;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AuditedAbstractEntity;


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
