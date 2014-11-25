package eu.factorx.awac.models.data.question;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.data.question.type.DoubleQuestion;
import eu.factorx.awac.models.data.question.type.IntegerQuestion;
import eu.factorx.awac.models.data.question.type.NumericQuestion;
import eu.factorx.awac.models.data.question.type.PercentageQuestion;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "driver")
public class Driver extends AuditedAbstractEntity {

    private static final long serialVersionUID = 5073847643332338205L;

    private String name;

    @OneToMany(mappedBy = "driver")
    private List<DriverValue> driverValueList;

    @OneToMany(mappedBy = "driver")
    private List<DoubleQuestion> doubleQuestionList;

    @OneToMany(mappedBy = "driver")
    private List<IntegerQuestion> integerQuestionList;

    @OneToMany(mappedBy = "driver")
    private List<PercentageQuestion> percentageQuestionList;

    public Driver() {
    }

	public Driver(String name) {
		this.name = name;
	}

	public List<DoubleQuestion> getDoubleQuestionList() {
        return doubleQuestionList;
    }

    public void setDoubleQuestionList(List<DoubleQuestion> doubleQuestionList) {
        this.doubleQuestionList = doubleQuestionList;
    }

    public List<IntegerQuestion> getIntegerQuestionList() {
        return integerQuestionList;
    }

    public void setIntegerQuestionList(List<IntegerQuestion> integerQuestionList) {
        this.integerQuestionList = integerQuestionList;
    }

    public List<PercentageQuestion> getPercentageQuestionList() {
        return percentageQuestionList;
    }

    public void setPercentageQuestionList(List<PercentageQuestion> percentageQuestionList) {
        this.percentageQuestionList = percentageQuestionList;
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
