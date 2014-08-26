package eu.factorx.awac.service.impl;

import java.util.List;

import eu.factorx.awac.models.code.type.ScopeTypeCode;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.service.ScopeService;

@Component
public class ScopeServiceImpl extends AbstractJPAPersistenceServiceImpl<Scope> implements ScopeService {

	@Override
	public Scope findBySite(Site site) {
		List<Scope> resultList = JPA.em().createNamedQuery(Scope.FIND_BY_SITE, Scope.class)
				.setParameter("site", site).setParameter("scopeType", ScopeTypeCode.SITE).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one scope with site = '" + site + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	@Override
	public Scope findByOrganization(Organization organization) {
		List<Scope> resultList = JPA.em().createNamedQuery(Scope.FIND_BY_ORGANIZATION, Scope.class)
				.setParameter("organization", organization).setParameter("scopeType", ScopeTypeCode.ORG).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one scope with organization = '" + organization + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	@Override
	public Scope findByProduct(Product product) {
		List<Scope> resultList = JPA.em().createNamedQuery(Scope.FIND_BY_PRODUCT, Scope.class)
				.setParameter("product", product).setParameter("scopeType", ScopeTypeCode.PRODUCT).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one scope with code = '" + product + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}
}
