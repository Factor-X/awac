package eu.factorx.awac.models.data;


//@Entity
//@Table(name = "double_answer")
public class DoubleAnswer extends NumericAnswer {

	private static final long serialVersionUID = 1L;
	private Double value;

	public DoubleAnswer() {
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double param) {
		this.value = param;
	}

}