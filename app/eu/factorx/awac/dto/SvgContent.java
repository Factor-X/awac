package eu.factorx.awac.dto;

import play.mvc.Content;

public class SvgContent implements Content {

	private final String content;

	public SvgContent(String content) {
		this.content = content;
	}

	@Override
	public String body() {
		return content;
	}

	@Override
	public String contentType() {
		return "image/svg+xml";
	}
}
