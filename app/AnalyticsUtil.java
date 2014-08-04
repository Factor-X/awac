import eu.factorx.awac.models.Analytics;
import org.hibernate.Session;
import org.joda.time.DateTime;
import play.db.jpa.JPA;
import play.mvc.Http;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AnalyticsUtil {


	public static Analytics start(Http.Request request) {
		Analytics analytics = new Analytics();

		analytics.setClientIP(request.remoteAddress());
		analytics.setHttpVersion(request.version());
		analytics.setMethod(request.method());
		analytics.setUri(request.uri());
		analytics.setAccept(request.getHeader("Accept"));
		analytics.setAcceptCharset(request.getHeader("Accept-Charset"));
		analytics.setAcceptEncoding(request.getHeader("Accept-Encoding"));
		analytics.setAcceptLanguage(request.getHeader("Accept-Language"));
		analytics.setAcceptDatetime(request.getHeader("Accept-Datetime"));
		analytics.setAuthorization(request.getHeader("Authorization"));
		analytics.setCacheControl(request.getHeader("Cache-Control"));
		analytics.setConnection(request.getHeader("Connection"));
		analytics.setCookie(request.getHeader("Cookie"));
		analytics.setContentLength(request.getHeader("Content-Length"));
		analytics.setContentMD5(request.getHeader("Content-MD5"));
		analytics.setContentType(request.getHeader("Content-Type"));
		analytics.setDate(request.getHeader("Date"));
		analytics.setExpect(request.getHeader("Expect"));
		analytics.setFrom(request.getHeader("From"));
		analytics.setHost(request.getHeader("Host"));
		analytics.setIfMatch(request.getHeader("If-Match"));
		analytics.setIfModifiedSince(request.getHeader("If-Modified-Since"));
		analytics.setIfNoneMatch(request.getHeader("If-None-Match"));
		analytics.setIfRange(request.getHeader("If-Range"));
		analytics.setIfUnmodifiedSince(request.getHeader("If-Unmodified-Since"));
		analytics.setMaxForwards(request.getHeader("Max-Forwards"));
		analytics.setOrigin(request.getHeader("Origin"));
		analytics.setPragma(request.getHeader("Pragma"));
		analytics.setProxyAuthorization(request.getHeader("Proxy-Authorization"));
		analytics.setRange(request.getHeader("Range"));
		analytics.setReferer(request.getHeader("Referer"));
		analytics.setTe(request.getHeader("TE"));
		analytics.setUserAgent(request.getHeader("User-Agent"));
		analytics.setUpgrade(request.getHeader("Upgrade"));
		analytics.setVia(request.getHeader("Via"));
		analytics.setWarning(request.getHeader("Warning"));

		analytics.setRequestStart(new DateTime());

		return analytics;
	}

	public static void end(Analytics analytics) {
		analytics.setRequestEnd(new DateTime());
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
		EntityManager em = emf.createEntityManager();
		JPA.bindForCurrentThread(em);
		Session session = JPA.em().unwrap(Session.class);
		session.saveOrUpdate(analytics);
	}
}
