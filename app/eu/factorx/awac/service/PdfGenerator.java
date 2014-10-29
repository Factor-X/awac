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

package eu.factorx.awac.service;

import java.io.OutputStream;

import play.api.templates.Html;
import play.mvc.Result;

public interface PdfGenerator {

	public Result ok(Html html) ;

	public byte[] toBytes(Html html);

	public byte[] toBytes(String string);

	public Result ok(Html html, String documentBaseURL) ;

	public byte[] toBytes(Html html, String documentBaseURL);

	public byte[] toBytes(String string, String documentBaseURL) ;

	public void toStream(String string, OutputStream os);

	public void toStream(String string, OutputStream os, String documentBaseURL) ;

	void setMemoryResource(String s, Object data);
}
