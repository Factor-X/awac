package eu.factorx.awac.models.code.type;

import javax.persistence.*;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "interfacetype")) })
public class InterfaceTypeCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final InterfaceTypeCode ENTERPRISE = new InterfaceTypeCode("enterprise");
	public static final InterfaceTypeCode MUNICIPALITY = new InterfaceTypeCode("municipality");
	public static final InterfaceTypeCode HOUSEHOLD = new InterfaceTypeCode("household");
    public static final InterfaceTypeCode EVENT = new InterfaceTypeCode("event");
    public static final InterfaceTypeCode LITTLE_EMITTER = new InterfaceTypeCode("little-emitter");
    public static final InterfaceTypeCode VERIFICATION = new InterfaceTypeCode("verification");
    public static final InterfaceTypeCode ADMIN = new InterfaceTypeCode("admin");

    public InterfaceTypeCode() {
        super(CodeList.INTERFACE_TYPE);
    }

    public InterfaceTypeCode(String key) {
        super(CodeList.INTERFACE_TYPE);
		this.key = key;
	}

    public ScopeTypeCode getScopeTypeCode() {

        if(key.equals(ENTERPRISE.getKey())){
            return ScopeTypeCode.SITE;
        }
        else if(key.equals(EVENT.getKey())){
            return ScopeTypeCode.PRODUCT;
        }
        else if(key.equals(HOUSEHOLD.getKey())){
            return ScopeTypeCode.ORG;
        }
        else if(key.equals(LITTLE_EMITTER.getKey())){
            return ScopeTypeCode.ORG;
        }
        else if(key.equals(MUNICIPALITY.getKey())){
            return ScopeTypeCode.ORG;
        }

        return null;
    }


    public static InterfaceTypeCode getByKey(String interfaceTypeCodeKey) {

        InterfaceTypeCode interfaceTypeCode=null;

        // InterfaceTypeCode.
        if (interfaceTypeCodeKey.equals(InterfaceTypeCode.ENTERPRISE.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.ENTERPRISE;
        } else if (interfaceTypeCodeKey.equals(InterfaceTypeCode.MUNICIPALITY.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.MUNICIPALITY;
        } else if (interfaceTypeCodeKey.equals(InterfaceTypeCode.HOUSEHOLD.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.HOUSEHOLD;
        } else if (interfaceTypeCodeKey.equals(InterfaceTypeCode.EVENT.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.EVENT;
        } else if (interfaceTypeCodeKey.equals(InterfaceTypeCode.LITTLE_EMITTER.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.LITTLE_EMITTER;
        }else if (interfaceTypeCodeKey.equals(InterfaceTypeCode.VERIFICATION.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.VERIFICATION;
        }else if (interfaceTypeCodeKey.equals(InterfaceTypeCode.ADMIN.getKey())) {
            interfaceTypeCode = InterfaceTypeCode.ADMIN;
        }
        return interfaceTypeCode;
    }
}