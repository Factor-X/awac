package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "public_private"))})
public class PublicPrivateCode extends Code {

	public static final PublicPrivateCode PUBLIC = new PublicPrivateCode("1");
	public static final PublicPrivateCode PRIVATE = new PublicPrivateCode("2");
	private static final long serialVersionUID = 1L;

	protected PublicPrivateCode() {
		super(CodeList.PUBLIC_PRIVATE);
	}

	public PublicPrivateCode(String key) {
		this();
		this.key = key;
	}
}