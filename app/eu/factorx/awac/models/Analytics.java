package eu.factorx.awac.models;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.joda.time.DateTime;
import org.joda.time.Period;

@Entity
public class Analytics extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	// start
	protected org.joda.time.DateTime requestStart;
	protected org.joda.time.DateTime requestEnd;
	protected int requestTime;

	// ip
	protected String clientIP = null;
	protected String httpVersion = null;
	protected String method = null;
	protected String uri = null;

	// headers
	protected String accept = null;
	protected String acceptCharset = null;
	protected String acceptEncoding = null;
	protected String acceptLanguage = null;
	protected String acceptDatetime = null;
	@Column(name = "header_authorization")
	protected String authorization = null;
	protected String cacheControl = null;
	protected String connection = null;
	protected String cookie = null;
	protected String contentLength = null;
	protected String contentMD5 = null;
	protected String contentType = null;
	protected String date = null;
	protected String expect = null;
	@Column(name = "header_from")
	protected String from = null;
	protected String host = null;
	protected String ifMatch = null;
	protected String ifModifiedSince = null;
	protected String ifNoneMatch = null;
	protected String ifRange = null;
	protected String ifUnmodifiedSince = null;
	protected String maxForwards = null;
	protected String origin = null;
	protected String pragma = null;
	protected String proxyAuthorization = null;
	protected String range = null;
	protected String referer = null;
	protected String te = null;
	protected String userAgent = null;
	protected String upgrade = null;
	protected String via = null;
	protected String warning = null;

	public Analytics() {
	}


	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAcceptCharset() {
		return acceptCharset;
	}

	public void setAcceptCharset(String acceptCharset) {
		this.acceptCharset = acceptCharset;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	public String getAcceptDatetime() {
		return acceptDatetime;
	}

	public void setAcceptDatetime(String acceptDatetime) {
		this.acceptDatetime = acceptDatetime;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getCacheControl() {
		return cacheControl;
	}

	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getContentLength() {
		return contentLength;
	}

	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentMD5() {
		return contentMD5;
	}

	public void setContentMD5(String contentMD5) {
		this.contentMD5 = contentMD5;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getExpect() {
		return expect;
	}

	public void setExpect(String expect) {
		this.expect = expect;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getIfMatch() {
		return ifMatch;
	}

	public void setIfMatch(String ifMatch) {
		this.ifMatch = ifMatch;
	}

	public String getIfModifiedSince() {
		return ifModifiedSince;
	}

	public void setIfModifiedSince(String ifModifiedSince) {
		this.ifModifiedSince = ifModifiedSince;
	}

	public String getIfNoneMatch() {
		return ifNoneMatch;
	}

	public void setIfNoneMatch(String ifNoneMatch) {
		this.ifNoneMatch = ifNoneMatch;
	}

	public String getIfRange() {
		return ifRange;
	}

	public void setIfRange(String ifRange) {
		this.ifRange = ifRange;
	}

	public String getIfUnmodifiedSince() {
		return ifUnmodifiedSince;
	}

	public void setIfUnmodifiedSince(String ifUnmodifiedSince) {
		this.ifUnmodifiedSince = ifUnmodifiedSince;
	}

	public String getMaxForwards() {
		return maxForwards;
	}

	public void setMaxForwards(String maxForwards) {
		this.maxForwards = maxForwards;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getPragma() {
		return pragma;
	}

	public void setPragma(String pragma) {
		this.pragma = pragma;
	}

	public String getProxyAuthorization() {
		return proxyAuthorization;
	}

	public void setProxyAuthorization(String proxyAuthorization) {
		this.proxyAuthorization = proxyAuthorization;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getTe() {
		return te;
	}

	public void setTe(String te) {
		this.te = te;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(String upgrade) {
		this.upgrade = upgrade;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public DateTime getRequestStart() {
		return requestStart;
	}

	public void setRequestStart(DateTime requestStart) {
		this.requestStart = requestStart;
		if (this.requestStart != null && this.requestEnd != null) {
			this.requestTime = new Period(this.requestStart, this.requestEnd).getMillis();
		}

	}

	public DateTime getRequestEnd() {
		return requestEnd;
	}

	public void setRequestEnd(DateTime requestEnd) {
		this.requestEnd = requestEnd;
		if (this.requestStart != null && this.requestEnd != null) {
			this.requestTime = new Period(this.requestStart, this.requestEnd).getMillis();
		}
	}

	public int getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(int requestTime) {
		this.requestTime = requestTime;
	}
}
