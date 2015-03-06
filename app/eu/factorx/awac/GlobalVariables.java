package eu.factorx.awac;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.models.knowledge.FactorValue;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalVariables {
	public static final String STARTUP_DATE_IDENTIFIER = DateTime.now().toString("YYYYMMdd_HHmmss");

	public final static Map<String, DTO> promises = new HashMap<>();

	public static List<FactorValue> factorValues = null;

}
