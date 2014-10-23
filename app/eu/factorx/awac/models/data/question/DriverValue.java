package eu.factorx.awac.models.data.question;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.knowledge.Period;

import javax.persistence.*;


@Entity
@Table(name = "drivervalue")
public class DriverValue extends AuditedAbstractEntity {

    @ManyToOne(optional = false)
    private Driver driver;

    @ManyToOne(optional = false)
    @JoinColumn(name="from_period_id", nullable=false)
    private Period fromPeriod;

    @Column(nullable = false,name = "defaultvalue")
    private Double defaultValue;


    public DriverValue() {
    }

    public DriverValue(Driver driver, Period fromPeriod, Double defaultValue) {
        this.driver = driver;
        this.fromPeriod = fromPeriod;
        this.defaultValue = defaultValue;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Period getFromPeriod() {
        return fromPeriod;
    }

    public void setFromPeriod(Period fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public Double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Double defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "DriverValue{" +
                "driver=" + driver +
                ", fromPeriod=" + fromPeriod +
                ", defaultValue=" + defaultValue +
                '}';
    }
}
