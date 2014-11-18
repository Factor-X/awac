package eu.factorx.awac.util;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import play.db.jpa.JPA;

public class JPAUtils {
	public static <T> TypedQuery<T> build(List<String> parts, Map<String, Object> parameters, Class<T> type) {
		// Build the query
		String jpql = StringUtils.join(parts, " ");
		TypedQuery<T> query = JPA.em().createQuery(jpql, type);

		// Fill the parameters
		for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
			query.setParameter(parameter.getKey(), parameter.getValue());
		}

		return query;
	}
}
