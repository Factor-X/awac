/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */

package eu.factorx.awac.controllers.vies;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

//for JSON
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import eu.factorx.awac.common.actions.CorsAction;
//import org.datanucleus.util.StringUtils;


@SuppressWarnings("serial")
@With(CorsAction.class)
public class VatViesService extends Controller {

    private static final String template = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:urn='urn:ec.europa.eu:taxud:vies:services:checkVat:types'><soapenv:Header/><soapenv:Body><urn:checkVatApprox><urn:countryCode>${countryCode}</urn:countryCode><urn:vatNumber>${vatNumber}</urn:vatNumber><urn:requesterCountryCode>${requesterCountryCode}</urn:requesterCountryCode><urn:requesterVatNumber>${requesterVatNumber}</urn:requesterVatNumber></urn:checkVatApprox></soapenv:Body></soapenv:Envelope>";

    public static Result checkVat(String uid, String vatNumber) {

        HashMap<String, String> responseData = new HashMap<String, String>();

        try {
            VatResult result = checkUID(uid, vatNumber);
            play.Logger.debug("<result>");
            play.Logger.debug("<valid>" + result.valid + "</valid>");
            play.Logger.debug("<name>" + StringEscapeUtils.escapeXml(result.name) + "</name>");
            play.Logger.debug("<address>" + StringEscapeUtils.escapeXml(result.address) + "</address>");
            play.Logger.debug("<requestid>" + result.id + "</requestid>");
            play.Logger.debug("<requestdate>" + result.date + "</requestdate>");
            play.Logger.debug("</result>");

            // compose HASHMAP result
            responseData.put("valid", new Boolean(result.valid).toString());
            responseData.put("name", result.name);
            responseData.put("address", result.address);
            responseData.put("requestid", result.id);
            responseData.put("requestdate", result.date);

        } catch (Exception e) {
            play.Logger.debug("error : " + e);
            responseData.put("error", "VIES can not handle the request");
            responseData.put("VAT UID", uid);
            responseData.put("VAT NR", vatNumber);

            return badRequest(Json.toJson(responseData));
        }

        //response().setHeader("Access-Control-Allow-Origin", "*");       // Need to add the correct domain in here!!
        //response().setHeader("Access-Control-Allow-Methods", "POST");   // Only allow POST
        //response().setHeader("Access-Control-Max-Age", "300");          // Cache response for 5 minutes
        //response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");    // Ensure this header is also allowed!
        return ok(Json.toJson(responseData));
    }

    public static VatResult checkUID(String uid, String requester) throws Exception {
        VatResult result = new VatResult();

        // Construct data
        String envelope = template.replace("${vatNumber}", uid.toUpperCase().substring(2)).replace("${countryCode}", uid.toUpperCase().substring(0, 2)).replace("${requesterVatNumber}", requester.toUpperCase().substring(2))
                .replace("${requesterCountryCode}", requester.toUpperCase().substring(0, 2));
        // Send data
        URL url = new URL("http://ec.europa.eu/taxation_customs/vies/services/checkVatService");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/soap+xml; charset=UTF-8;action=\"checkVatService\"");
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        play.Logger.debug(envelope);
        wr.write(envelope);
        wr.flush();
        play.Logger.debug("call OUT...");

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        String resultString = StringEscapeUtils.unescapeHtml4(sb.toString());
        result.valid = Boolean.valueOf(getValue(resultString, "valid"));
        if (result.valid) {
            result.name = getValue(resultString.replace("\n", " "), "traderName");
            result.address = getValue(resultString.replace("\n", ", "), "traderAddress");
            result.id = getValue(resultString, "requestIdentifier");
            result.date = getValue(resultString, "requestDate");
        }

        wr.close();
        rd.close();
        play.Logger.debug("Response IN...");

        return result;
    }


    private static class VatResult {
        public boolean valid = false;
        public String name;
        public String address;
        public String id;
        public String date;
    }

    private static String getValue(String text, String tag) {
        Pattern p = Pattern.compile("<" + tag + ">(.*)</" + tag + ">");
        Matcher m = p.matcher(text);
        return m.find() && m.group(1) != null ? m.group(1) : "";
    }

    /**
     * *************** TEST FOLLOWS ***********************
     */

    public static Result checkRimshotVat() {
        HashMap<String, String> responseData = new HashMap<String, String>();
        try {
            VatResult result = checkUID("BE0849213125", "BE0849213125");
            play.Logger.debug("<result>");
            play.Logger.debug("<valid>" + result.valid + "</valid>");
            play.Logger.debug("<name>" + StringEscapeUtils.escapeXml(result.name) + "</name>");
            play.Logger.debug("<address>" + StringEscapeUtils.escapeXml(result.address) + "</address>");
            play.Logger.debug("<requestid>" + result.id + "</requestid>");
            play.Logger.debug("<requestdate>" + result.date + "</requestdate>");
            play.Logger.debug("</result>");
            // compose HASHMAP result
            responseData.put("valid", new Boolean(result.valid).toString());
            responseData.put("name", result.name);
            responseData.put("address", result.address);
            responseData.put("requestid", result.id);
            responseData.put("requestdate", result.date);

        } catch (Exception e) {
            play.Logger.debug("error : " + e);
            responseData.put("error", "VIES can not handle the request");
            responseData.put("VAT UID", "BE0849213125");
            responseData.put("VAT NR", "BE0849213125");
            return badRequest(Json.toJson(responseData));
        }
        return ok(Json.toJson(responseData));
    }
}
