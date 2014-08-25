package eu.factorx.awac.common.actions;

import eu.factorx.awac.controllers.SecuredController;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.util.BusinessErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import play.Logger;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Http.Response;
import play.mvc.Security;
import play.mvc.SimpleResult;


@Service
public class SecurityAnnotationAction extends Action<SecurityAnnotation> {


	@Autowired
	private SecuredController securedController;

	@Override
	public F.Promise<SimpleResult> call(Http.Context context) throws Throwable {
		Logger.info("SecurityAnnotationAction - entering SecurityAnnotationAction...");
		Logger.info("SecurityAnnotationAction - requested rights required for admin:" + configuration.isAdmin());
		Logger.info("SecurityAnnotationAction - requested rights required for systemAdmin:" + configuration.isSystemAdmin());
		Logger.info("SecurityAnnotationAction - securedController.isAuthenticated() returns:" + securedController.isAuthenticated());
		Logger.info("SecurityAnnotationAction - securedController.isAdministrator() returns:" + securedController.isAdministrator());
		Logger.info("SecurityAnnotationAction - securedController.isSystemAdministrator() returns:" + securedController.isSystemAdministrator());

		// Check if access right required is systemAdmin and if account has the systemAdmin right
		if (configuration.isSystemAdmin() && (configuration.isSystemAdmin()==securedController.isSystemAdministrator())) {
			Logger.info ("SecurityAnnotationAction - System Admin rights granted");
		} else if (configuration.isSystemAdmin()) {
			Logger.info ("SecurityAnnotationAction - System Admin rights NOT granted");
			return F.Promise.promise(new F.Function0<SimpleResult>() {
				@Override
				public SimpleResult apply() throws Throwable {
					return unauthorized(new ExceptionsDTO(BusinessErrorType.ACCESS_NOT_SYSTEM_ADMIN));
					//return unauthorized(Exceptions.ACCESS_NOT_SYSTEM_ADMIN);
				}
			});
			//F.Promise.pure(unauthorized(Exceptions.ACCESS_NOT_SYSTEM_ADMIN));
		}

		// Check if access right required is Admin and if account has the Admin right
		if (configuration.isAdmin() && (configuration.isAdmin()==securedController.isAdministrator())) {
			Logger.info ("SecurityAnnotationAction - Admin rights granted");
		} else if (configuration.isAdmin()) {
			Logger.info ("SecurityAnnotationAction - Admin rights NOT granted");
			return F.Promise.promise(new F.Function0<SimpleResult>() {
				@Override
				public SimpleResult apply() throws Throwable {
					return unauthorized(new ExceptionsDTO(BusinessErrorType.ACCESS_NOT_ADMIN));
				}
			});
		}

		return delegate.call(context);
	}
}