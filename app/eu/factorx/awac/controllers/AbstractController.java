package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import play.mvc.Controller;


@org.springframework.stereotype.Controller
public class AbstractController  extends Controller {


	@Autowired
	protected SecuredController securedController;

	protected static <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		T dto = DTO.getDTO(request().body().asJson(), DTOclass);
		if (dto == null) {
			throw new RuntimeException("The request content cannot be converted to a '" + DTOclass.getName() + "'.");
		}
		return dto;
	}
}
