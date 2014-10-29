package eu.factorx.awac.common.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import play.Logger;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;
import eu.factorx.awac.controllers.SecuredController;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.util.BusinessErrorType;


@Service
public class SecurityAnnotationAction extends Action<SecurityAnnotation> {


    @Autowired
    private SecuredController securedController;

    @Override
    public F.Promise<SimpleResult> call(Http.Context context) throws Throwable {

        // Check if access right required is systemAdmin and if account has the systemAdmin right
        if (configuration.isSystemAdmin() && (configuration.isSystemAdmin() == securedController.isSystemAdministrator())) {
            Logger.debug("SecurityAnnotationAction - System Admin rights granted");
        } else if (configuration.isSystemAdmin()) {
            Logger.debug("SecurityAnnotationAction - System Admin rights NOT granted");
            return F.Promise.promise(new F.Function0<SimpleResult>() {
                @Override
                public SimpleResult apply() throws Throwable {
                    return unauthorized(new ExceptionsDTO(BusinessErrorType.WRONG_RIGHT));
                }
            });
        }


        if (configuration.isAdmin()) {
            if (!securedController.isAdministrator()) {
                return F.Promise.promise(new F.Function0<SimpleResult>() {
                    @Override
                    public SimpleResult apply() throws Throwable {
                        return unauthorized(new ExceptionsDTO(BusinessErrorType.WRONG_RIGHT));
                    }
                });
            }
        } else if (configuration.isMainVerifier()) {

            if (!securedController.isMainVerifier() && !securedController.isAdministrator()) {
                return F.Promise.promise(new F.Function0<SimpleResult>() {
                    @Override
                    public SimpleResult apply() throws Throwable {
                        return unauthorized(new ExceptionsDTO(BusinessErrorType.WRONG_RIGHT));
                    }
                });
            }
        }

        return delegate.call(context);
    }
}