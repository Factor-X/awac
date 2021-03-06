package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.models.code.type.ScopeTypeCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.dto.awac.post.AssignPeriodToSiteDTO;
import eu.factorx.awac.dto.awac.post.ListPeriodsDTO;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.AccountSiteAssociationService;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.service.SiteService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;


@org.springframework.stereotype.Controller
public class SiteController  extends AbstractController {

	@Autowired
	private SiteService siteService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private PeriodService periodService;

    @Autowired
    private AccountSiteAssociationService accountSiteAssociationService;


	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result edit(){

        //control
        if(!securedController.getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.SITE)){
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_RIGHT);
        }

		SiteDTO dto = extractDTOFromRequest(SiteDTO.class);

		//control id
		if(dto.getId()== null){
			throw new MyrmexRuntimeException(BusinessErrorType.DATA_NOT_FOUND);
		}

		//load site
		Site site = siteService.findById(dto.getId());

		// if the site is null
		if(site == null){
			throw new MyrmexRuntimeException(BusinessErrorType.DATA_NOT_FOUND);
		}

		//edit
		site.setName(dto.getName());
		site.setNaceCode(dto.getNaceCode());
		site.setDescription(dto.getDescription());

		site.setOrganizationalStructure(dto.getOrganizationalStructure());
		site.setPercentOwned(dto.getPercentOwned());

		siteService.saveOrUpdate(site);


		return ok(conversionService.convert(site,SiteDTO.class));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result create(){

        //control
        if(!securedController.getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.SITE)){
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_RIGHT);
        }

		SiteDTO dto = extractDTOFromRequest(SiteDTO.class);

		//create
		Site site = new Site(securedController.getCurrentUser().getOrganization(), dto.getName());

		site.setNaceCode(dto.getNaceCode());
		site.setDescription(dto.getDescription());

		site.setOrganizationalStructure(dto.getOrganizationalStructure());
		site.setPercentOwned(dto.getPercentOwned());

        //assign the last year
        Period lastYear = periodService.findLastYear();
        List<Period> periodList = new ArrayList<>();
        periodList.add(lastYear);
        site.setListPeriodAvailable(periodList);

        siteService.saveOrUpdate(site);

        //assign the creator to the site
        AccountSiteAssociation accountSiteAssociation = new AccountSiteAssociation(site ,securedController.getCurrentUser());
        accountSiteAssociationService.saveOrUpdate(accountSiteAssociation);

		// return the new site because some new information are added, like id and scope
		return ok(conversionService.convert(site,SiteDTO.class));
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
	public Result assignPeriodToSite(){

        //control
        if(!securedController.getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.SITE)){
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_RIGHT);
        }

		AssignPeriodToSiteDTO dto = extractDTOFromRequest(AssignPeriodToSiteDTO.class);


		//load period
		Period period = periodService.findByCode(new PeriodCode(dto.getPeriodKeyCode()));

		if(period==null){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//load site
		Site site = siteService.findById(dto.getSiteId());

		if(site == null || !site.getOrganization().equals(securedController.getCurrentUser().getOrganization())){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//assign period to site
		boolean toAdd = dto.getAssign();

		//control is the period is already into site
		if(site.getListPeriodAvailable()!=null){
			for(Period periodToTest : site.getListPeriodAvailable()){

				if(periodToTest.equals(period)){
					//founded and to assign => useless to add
					if(dto.getAssign()){
						toAdd = false;
					}
					//founded and to remove => remove
					else{
						site.getListPeriodAvailable().remove(periodToTest);
					}
					break;
				}
			}
		}

		//add ig it's needed
		if(toAdd){
			site.getListPeriodAvailable().add(period);
		}

		//save
		siteService.saveOrUpdate(site);

        ListPeriodsDTO listPeriodsDTO = conversionService.convert(site, ListPeriodsDTO.class);

        return ok(listPeriodsDTO);
	}

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	@SecurityAnnotation(isAdmin = false, isSystemAdmin = false)
	public Result getSite(long siteId){

        //control
        if(!securedController.getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.SITE)){
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_RIGHT);
        }

		//load the site
		Site site = siteService.findById(siteId);

		if(site == null){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//test owner
		if(!securedController.getCurrentUser().getOrganization().equals(site.getOrganization())){
			throw new MyrmexRuntimeException("");
			//TODO error
		}

		//convert
		return  ok(conversionService.convert(site,SiteDTO.class));
	}
}
