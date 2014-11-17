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

package eu.factorx.awac.service.impl;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.stereotype.Component;
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

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;

import eu.factorx.awac.service.PdfGenerator;

@Component
public class PdfGeneratorImpl implements PdfGenerator {

	private final String PLAY_DEFAULT_URL = "http://localhost:9000";
	private final ITextRenderer         renderer;
	private final PdfGeneratorUserAgent userAgent;

	public PdfGeneratorImpl() throws IOException, DocumentException {
		renderer = new ITextRenderer();
		//addFontDirectory(renderer.getFontResolver(), Play.current().path() + "/public/fonts");
		userAgent = new PdfGeneratorUserAgent(renderer.getOutputDevice());
		userAgent.setSharedContext(renderer.getSharedContext());
		renderer.getSharedContext().setUserAgentCallback(userAgent);

	}

	public Result ok(Html html) {
		byte[] pdf = toBytes(tidify(html.body()));
		return Results.ok(pdf).as("application/pdf");
	}

	public byte[] toBytes(Html html) {
		byte[] pdf = toBytes(tidify(html.body()));
		return pdf;
	}

	public byte[] toBytes(String string) {
		return toBytes(string, PLAY_DEFAULT_URL);
	}

	private String tidify(String body) {
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

	public Result ok(Html html, String documentBaseURL) {
		byte[] pdf = toBytes(tidify(html.body()), documentBaseURL);
		return Results.ok(pdf).as("application/pdf");
	}

	public byte[] toBytes(Html html, String documentBaseURL) {
		byte[] pdf = toBytes(tidify(html.body()), documentBaseURL);
		return pdf;
	}

	public byte[] toBytes(String string, String documentBaseURL) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		toStream(string, os, documentBaseURL);
		return os.toByteArray();
	}

	public void toStream(String string, OutputStream os) {
		toStream(string, os, PLAY_DEFAULT_URL);
	}

	public void toStream(String string, OutputStream os, String documentBaseURL) {
		try {
			Reader reader = new StringReader(string);

			XMLResource xmlResource = XMLResource.load(reader);
			Document document = xmlResource.getDocument();

			renderer.setDocument(document, documentBaseURL);
			renderer.layout();

			renderer.createPDF(os);
		} catch (Exception e) {
			Logger.error("Creating document from template", e);
		}
	}

	@Override
	public void setMemoryResource(String s, Object data) {
		userAgent.setMemoryResource(s, data);
	}

	private void addFontDirectory(ITextFontResolver fontResolver,
	                              String directory) throws DocumentException, IOException {
		Logger.info("PDF - directory parameter:" + directory);

		Logger.info("PDF - getting file list from currentPath()");
		File fileList = Play.application(Play.current()).path();
		for (File file : fileList.listFiles()) {
			Logger.info("file :" + file.getAbsolutePath());
		}


		File dir = new File(directory);
		if (dir != null) {
			Logger.info("PDF - dir is not null");
			for (File file : dir.listFiles()) {
				Logger.info("file :" + file.getAbsolutePath());
				fontResolver.addFont(file.getAbsolutePath(), BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			}
		} else {
			Logger.info("PDF - dir is null");
		}
	}

	public class PdfGeneratorUserAgent extends ITextUserAgent {

		private Map<String, Object> memoryData;

		public PdfGeneratorUserAgent(ITextOutputDevice outputDevice) {
			super(outputDevice);
			memoryData = new HashMap<>();
		}

		@Override
		public ImageResource getImageResource(String uri) {

			if (memoryData.containsKey(uri)) {
				URI u = URI.create(uri);
				if (u.getHost().toLowerCase().equals("svg")) {
					try {
						TranscoderInput input_svg_image = new TranscoderInput(new StringReader(memoryData.get(uri).toString()));
						ByteArrayOutputStream png_ostream = new ByteArrayOutputStream();
						TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);
						PNGTranscoder my_converter = new PNGTranscoder();
						my_converter.transcode(input_svg_image, output_png_image);
						png_ostream.flush();
						png_ostream.close();
						Image image = Image.getInstance(getData(new ByteArrayInputStream(png_ostream.toByteArray())));
						scaleToOutputResolution(image);
						return new ImageResource(new ITextFSImage(image));
					} catch (Exception e) {
						Logger.error("fetching image " + uri, e);
						throw new RuntimeException(e);
					}
				} else {
					String message = "Unrecognized memory Resource " + uri;
					Logger.error(message);
					throw new RuntimeException(message);
				}
			} else {

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
					ImageResource imageResource = super.getImageResource(uri);

					return imageResource;

				}
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

		public void setMemoryResource(String s, Object data) {
			memoryData.put(s, data);
		}
	}

}
