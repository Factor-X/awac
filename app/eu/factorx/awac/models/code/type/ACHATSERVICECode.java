
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "achatservice"))})
public class ACHATSERVICECode extends Code {

    private static final long serialVersionUID = 1L;

    protected ACHATSERVICECode() {
        super(CodeList.ACHATSERVICE);
    }

    public ACHATSERVICECode(String key) {
        this();
        this.key = key;
    }
public static final ACHATSERVICECode SERVICES_FAIBLEMENT_MATERIELS = new ACHATSERVICECode("1");
public static final ACHATSERVICECode SERVICES_FORTEMENT_MATERIELS = new ACHATSERVICECode("2");
public static final ACHATSERVICECode INFORMATIQUE_ET_CONSOMMABLES = new ACHATSERVICECode("3");}