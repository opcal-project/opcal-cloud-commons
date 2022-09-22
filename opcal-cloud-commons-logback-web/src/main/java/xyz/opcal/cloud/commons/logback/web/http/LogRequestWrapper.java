/*
 *  Copyright 2020-2022 Opcal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.opcal.cloud.commons.logback.web.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import lombok.Getter;

public class LogRequestWrapper extends HttpServletRequestWrapper {

	@Getter
	private final String requestId;
	private final String requestBody;

	public LogRequestWrapper(String requestId, HttpServletRequest request, String requestBody) {
		super(request);
		this.requestId = requestId;
		this.requestBody = requestBody;
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
