package com.services.direct.data.output;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;

import lombok.Data;

@Data
public class PdfViwerOutput {

	@NotNull(message = "filename is required.")
	private String filename;
	private String basename;
	private String extension;
	private String contentType;
	private long contentLength;

	/**
	 * @param filename
	 * @param contentType
	 * @param contentLength
	 */
	public PdfViwerOutput(String filename, String contentType, long contentLength) {
		super();
		this.filename = filename;
		this.contentType = contentType;
		this.contentLength = contentLength;
		this.basename = FilenameUtils.getBaseName(filename);
		this.extension = FilenameUtils.getExtension(filename);
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	
	public String getBasename() {
		return basename;
	}

	public void setBasename(String basename) {
		this.basename = basename;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "City{" + "filename=" + filename + ", type=" + contentType + ", contentLength=" + contentLength + '}';
	}
}
