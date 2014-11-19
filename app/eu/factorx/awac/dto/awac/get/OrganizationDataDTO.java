package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.models.knowledge.Period;

import java.util.HashMap;

/**
 * Created by florian on 19/11/14.
 */
public class OrganizationDataDTO extends DTO{

	private OrganizationDTO organization;

	private Integer siteNb = 0;
	private Integer productNb = 0;
	private HashMap<String,Integer> closedFormMap;

	public OrganizationDataDTO() {
	}

	public OrganizationDTO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDTO organization) {
		this.organization = organization;
	}

	public Integer getSiteNb() {
		return siteNb;
	}

	public void setSiteNb(Integer siteNb) {
		this.siteNb = siteNb;
	}

	public Integer getProductNb() {
		return productNb;
	}

	public void setProductNb(Integer productNb) {
		this.productNb = productNb;
	}


	public HashMap<String, Integer> getClosedFormMap() {
		return closedFormMap;
	}

	public void setClosedFormMap(HashMap<String, Integer> closedFormMap) {
		this.closedFormMap = closedFormMap;
	}

	public void putClosedForm(String periodKey,Integer value){
		if(closedFormMap==null){
			closedFormMap = new HashMap<>();
		}
		closedFormMap.put(periodKey,value);
	}

	public void incrementClosedForm(String periodKey){
		if(closedFormMap==null){
			closedFormMap = new HashMap<>();
		}
		if(closedFormMap.containsKey(periodKey)){
			closedFormMap.put(periodKey,closedFormMap.get(periodKey)+1);
		}
		else{
			closedFormMap.put(periodKey,1);
		}
	}

	@Override
	public String toString() {
		return "OrganizationDataDTO{" +
				"organization=" + organization +
				", siteNb=" + siteNb +
				", productNb=" + productNb +
				'}';
	}
}
