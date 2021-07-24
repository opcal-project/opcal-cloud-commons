package xyz.opcal.cloud.commons.logback.web.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.bouncycastle.util.io.TeeOutputStream;

import lombok.Getter;

public class LogResponseWrapper extends HttpServletResponseWrapper {

	@Getter
	private String requestId;

	private ByteArrayOutputStream baos = new ByteArrayOutputStream();

	public LogResponseWrapper(String requestId, HttpServletResponse response) {
		super(response);
		this.requestId = requestId;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		TeeOutputStream teeOS = new TeeOutputStream(super.getOutputStream(), baos);
		return new ServletOutputStream() {

			@Override
			public void write(int b) throws IOException {
				teeOS.write(b);
			}

			@Override
			public void setWriteListener(WriteListener listener) {
				// do nothing
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void close() throws IOException {
				teeOS.close();
			}
		};
	}

	public byte[] getResponseBytes() {
		return baos.toByteArray();
	}

	public String getResponseString() {
		return new String(getResponseBytes(), StandardCharsets.UTF_8);
	}

	@Override
	public String toString() {
		return getResponseString();
	}
}
