package eu.factorx.awac.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.SvgContent;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import play.Logger;
import play.api.templates.Html;
import play.mvc.Controller;
import scala.collection.mutable.StringBuilder;


@org.springframework.stereotype.Controller
public class AbstractController extends Controller {


	@Autowired
	protected SecuredController securedController;

	protected static <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		JsonNode json = request().body().asJson();
		T dto = DTO.getDTO(json, DTOclass);
		if (dto == null) {
			throw new MyrmexRuntimeException(BusinessErrorType.CONVERSION_DTO_ERROR,DTOclass.getName());
		}
		return dto;
	}

	protected static Html toHtml(String str) {
		scala.collection.mutable.StringBuilder sb = new StringBuilder(str);
		Html html = new Html(sb);
		return html;
	}

	protected static SvgContent toSvg(String str) {
		return new SvgContent(str);
	}

	protected void markNoCache() {
		response().setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response().setHeader("Pragma", "no-cache"); // HTTP 1.0.
	}
}
