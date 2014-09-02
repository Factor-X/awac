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

package eu.factorx.awac.util.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.*;
import org.xhtmlrenderer.resource.CSSResource;
import org.xhtmlrenderer.resource.ImageResource;
import org.xhtmlrenderer.resource.XMLResource;
import play.Logger;
import play.api.Play;
import play.api.templates.Html;
import play.mvc.Result;
import play.mvc.Results;
import scala.Option;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class PDF {

	private static final String PLAY_DEFAULT_URL = "http://localhost:9000";

	public static Result ok(Html html) {
		byte[] pdf = toBytes(tidify(html.body()));
		return Results.ok(pdf).as("application/pdf");
	}

	public static byte[] toBytes(Html html) {
		byte[] pdf = toBytes(tidify(html.body()));
		return pdf;
	}

	public static byte[] toBytes(String string) {
		return toBytes(string, PLAY_DEFAULT_URL);
	}

	private static String tidify(String body) {
		Tidy tidy = new Tidy();
		// configure jtidy to avoid logging
		tidy.setShowErrors(0);
		tidy.setQuiet(true);
		tidy.setShowWarnings(false);
		//tidy.setErrout(null);
		tidy.setXHTML(true);
		StringWriter writer = new StringWriter();
		tidy.parse(new StringReader(body), writer);
		return writer.getBuffer().toString();
	}

	public static Result ok(Html html, String documentBaseURL) {
		byte[] pdf = toBytes(tidify(html.body()), documentBaseURL);
		return Results.ok(pdf).as("application/pdf");
	}

	public static byte[] toBytes(Html html, String documentBaseURL) {
		byte[] pdf = toBytes(tidify(html.body()), documentBaseURL);
		return pdf;
	}

	public static byte[] toBytes(String string, String documentBaseURL) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		toStream(string, os, documentBaseURL);
		return os.toByteArray();
	}

	public static void toStream(String string, OutputStream os) {
		toStream(string, os, PLAY_DEFAULT_URL);
	}

	public static void toStream(String string, OutputStream os, String documentBaseURL) {
		try {
			Reader reader = new StringReader(string);
			ITextRenderer renderer = new ITextRenderer();
			addFontDirectory(renderer.getFontResolver(), Play.current().path()
					+ "/public/fonts");
			MyUserAgent myUserAgent = new MyUserAgent(
					renderer.getOutputDevice());
			myUserAgent.setSharedContext(renderer.getSharedContext());
			renderer.getSharedContext().setUserAgentCallback(myUserAgent);
			XMLResource xmlResource = XMLResource.load(reader);
			Document document = xmlResource.getDocument();
			renderer.setDocument(document, documentBaseURL);
			renderer.layout();
			renderer.createPDF(os);
		} catch (Exception e) {
			Logger.error("Creating document from template", e);
		}
	}

	private static void addFontDirectory(ITextFontResolver fontResolver,
	                                     String directory) throws DocumentException, IOException {
		File dir = new File(directory);
		for (File file : dir.listFiles()) {
			fontResolver.addFont(file.getAbsolutePath(), BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
		}
	}

	public static class MyUserAgent extends ITextUserAgent {

		public MyUserAgent(ITextOutputDevice outputDevice) {
			super(outputDevice);
		}

		@Override
		public ImageResource getImageResource(String uri) {
			Option<InputStream> option = Play.current().resourceAsStream(uri);
			if (option.isDefined()) {
				InputStream stream = option.get();
				try {
					Image image = Image.getInstance(getData(stream));
					scaleToOutputResolution(image);
					return new ImageResource(new ITextFSImage(image));
				} catch (Exception e) {
					Logger.error("fetching image " + uri, e);
					throw new RuntimeException(e);
				}
			} else {
				return super.getImageResource(uri);
			}
		}

		@Override
		public CSSResource getCSSResource(String uri) {
			try {
				// uri is in fact a complete URL
				String path = new URL(uri).getPath();
				Option<InputStream> option = Play.current().resourceAsStream(
						path);
				if (option.isDefined()) {
					return new CSSResource(option.get());
				} else {
					return super.getCSSResource(uri);
				}
			} catch (MalformedURLException e) {
				Logger.error("fetching css " + uri, e);
				throw new RuntimeException(e);
			}
		}

		@Override
		public byte[] getBinaryResource(String uri) {
			Option<InputStream> option = Play.current().resourceAsStream(uri);
			if (option.isDefined()) {
				InputStream stream = option.get();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					copy(stream, baos);
				} catch (IOException e) {
					Logger.error("fetching binary " + uri, e);
					throw new RuntimeException(e);
				}
				return baos.toByteArray();
			} else {
				return super.getBinaryResource(uri);
			}
		}

		@Override
		public XMLResource getXMLResource(String uri) {
			Option<InputStream> option = Play.current().resourceAsStream(uri);
			if (option.isDefined()) {
				return XMLResource.load(option.get());
			} else {
				return super.getXMLResource(uri);
			}
		}

		private void scaleToOutputResolution(Image image) {
			float factor = getSharedContext().getDotsPerPixel();
			image.scaleAbsolute(image.getPlainWidth() * factor,
					image.getPlainHeight() * factor);
		}

		private byte[] getData(InputStream stream) throws IOException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			copy(stream, baos);
			return baos.toByteArray();
		}

		private void copy(InputStream stream, OutputStream os)
				throws IOException {
			byte[] buffer = new byte[1024];
			while (true) {
				int len = stream.read(buffer);
				os.write(buffer, 0, len);
				if (len < buffer.length)
					break;
			}
		}
	}

}
