package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.ProductDTO;

import java.util.List;

/**
 * Created by florian on 26/11/14.
 */
public class ProductAddUsersDTO extends DTO {

	private ProductDTO product;

	private List<AccountDTO> selectedAccounts;

	public ProductAddUsersDTO() {
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public List<AccountDTO> getSelectedAccounts() {
		return selectedAccounts;
	}

	public void setSelectedAccounts(List<AccountDTO> selectedAccounts) {
		this.selectedAccounts = selectedAccounts;
	}


	@Override
	public String toString() {
		return "ProductAddUsersDTO{" +
				"product=" + product +
				", selectedAccounts=" + selectedAccounts +
				'}';
	}
}
