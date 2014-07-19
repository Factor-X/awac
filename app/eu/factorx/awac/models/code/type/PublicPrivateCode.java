package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "public_private"))})
public class PublicPrivateCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final PublicPrivateCode PUBLIC = new PublicPrivateCode("1");
	public static final PublicPrivateCode PRIVATE = new PublicPrivateCode("2");

	protected PublicPrivateCode() {
		super(CodeList.PUBLIC_PRIVATE);
	}

	public PublicPrivateCode(String key) {
		this();
		this.key = key;
	}
}