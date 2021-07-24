package xyz.opcal.cloud.commons.logback.web.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;
import lombok.Getter;
import xyz.opcal.cloud.commons.logback.web.LogWebConstants;

public class LogRequestWrapper extends HttpServletRequestWrapper {

	@Getter
	private String requestId;
	private String requestBody;

	public LogRequestWrapper(String requestId, HttpServletRequest request, String requestBody) {
		super(request);
		this.requestId = requestId;
		this.requestBody = requestBody;
	}

	public boolean hasRequestId() {
		return !StringUtils.isBlank(super.getHeader(LogWebConstants.HEADER_X_REQUEST_ID));
	}

	@Override
	public String getHeader(String name) {
		if (StringUtils.equalsIgnoreCase(name, LogWebConstants.HEADER_X_REQUEST_ID) && !hasRequestId()) {
			return requestId;
		}
		return super.getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		if (!hasRequestId()) {
			List<String> names = Collections.list(super.getHeaderNames());
			names.add(LogWebConstants.HEADER_X_REQUEST_ID);
			return Collections.enumeration(names);
		}
		return super.getHeaderNames();
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		if (StringUtils.equalsIgnoreCase(name, LogWebConstants.HEADER_X_REQUEST_ID) && !hasRequestId()) {
			return Collections.enumeration(Arrays.asList(requestId));
		}
		return super.getHeaders(name);
	}


	@Override
	public ServletInputStream getInputStream() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8));

		return new ServletInputStream() {

			private boolean finished = false;

			@Override
			public int read() throws IOException {
				int data = bais.read();
				if (data == -1) {
					this.finished = true;
				}
				return data;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				// do nothing
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public boolean isFinished() {
				return finished;
			}

			@Override
			public void close() throws IOException {
				LogRequestWrapper.this.getRequest().getInputStream().close();
			}

		};
	}

	@Override
	public String toString() {
		return requestBody;
	}
}
