package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.dto.awac.shared.ReturnDTO;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.service.SiteService;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


@org.springframework.stereotype.Controller
public class SiteController  extends AbstractController {

	@Autowired
	private SiteService siteService;


	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = false, isSystemAdmin = false)
	public Result edit(){

		SiteDTO dto = extractDTOFromRequest(SiteDTO.class);

		//control id
		if(dto.getId()== null){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//load site
		Site site = siteService.findById(dto.getId());

		// if the site is null
		if(site == null){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//edit
		site.setName(dto.getName());
		site.setNaceCode(dto.getNaceCode());
		site.setDescription(dto.getDescription());

		site.setOrganizationalStructure(dto.getOrganizationalStructure());
		site.setEconomicInterest(dto.getEconomicInterest());
		site.setOperatingPolicy(dto.getOperatingPolicy());
		site.setAccountingTreatment(dto.getAccountingTreatment());
		site.setPercentOwned(dto.getPercentOwned());

		siteService.saveOrUpdate(site);


		return ok(new ReturnDTO());
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = false, isSystemAdmin = false)
	public Result create(){

		SiteDTO dto = extractDTOFromRequest(SiteDTO.class);

		//create
		Site site = new Site(securedController.getCurrentUser().getOrganization(), dto.getName());

		site.setNaceCode(dto.getNaceCode());
		site.setDescription(dto.getDescription());

		site.setOrganizationalStructure(dto.getOrganizationalStructure());
		site.setEconomicInterest(dto.getEconomicInterest());
		site.setOperatingPolicy(dto.getOperatingPolicy());
		site.setAccountingTreatment(dto.getAccountingTreatment());
		site.setPercentOwned(dto.getPercentOwned());

		siteService.saveOrUpdate(site);


		return ok(new ReturnDTO());
	}
}
