package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.post.ProductCreateFormDTO;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by root on 7/07/14.
 */
@org.springframework.stereotype.Controller
public class AnswerController extends Controller {

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getByForm(Integer formId, Integer periodId, Integer scopeId) {

        ProductCreateFormDTO productCreateFormDTO = DTO.getDTO(request().body().asJson(), ProductCreateFormDTO.class);

        return null;
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result save() {
        return null;
    }
}
