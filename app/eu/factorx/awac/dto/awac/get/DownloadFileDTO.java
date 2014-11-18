package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class DownloadFileDTO extends DTO {

	private String filename;

	private String mimeType;

	private String base64;

	public DownloadFileDTO() {
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
}
