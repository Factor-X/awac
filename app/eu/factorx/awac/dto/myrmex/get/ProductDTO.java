package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;

/**
 * Created by root on 6/07/14.
 */
public class ProductDTO extends DTO {

	private String name;

	public ProductDTO() {
	}

	public ProductDTO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
