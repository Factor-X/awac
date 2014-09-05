package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "interfacetype")) })
public class InterfaceTypeCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final InterfaceTypeCode ENTERPRISE = new InterfaceTypeCode("enterprise");
	public static final InterfaceTypeCode MUNICIPALITY = new InterfaceTypeCode("municipality");
	public static final InterfaceTypeCode HOUSEHOLD = new InterfaceTypeCode("household");

	public InterfaceTypeCode() {
		super(CodeList.INTERFACE_TYPE);
	}

	public InterfaceTypeCode(String key) {
		this();
		this.key = key;
	}
}