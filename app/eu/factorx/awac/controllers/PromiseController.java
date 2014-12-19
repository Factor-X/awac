package eu.factorx.awac.controllers;

import eu.factorx.awac.GlobalVariables;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.PromiseDTO;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

@org.springframework.stereotype.Controller
public class PromiseController extends AbstractController {

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result fetch(String uuid) {
		if (GlobalVariables.promises.containsKey(uuid)) {
			DTO dto = GlobalVariables.promises.get(uuid);
			GlobalVariables.promises.remove(uuid);
			return ok(dto);
		}else{
			return ok(new PromiseDTO(uuid));
		}
	}

}
