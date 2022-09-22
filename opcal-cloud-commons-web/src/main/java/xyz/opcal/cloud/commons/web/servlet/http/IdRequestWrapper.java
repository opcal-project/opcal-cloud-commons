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

package xyz.opcal.cloud.commons.web.servlet.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import xyz.opcal.cloud.commons.web.WebConstants;

public class IdRequestWrapper extends HttpServletRequestWrapper {

	@Getter
	private final String requestId;

	public IdRequestWrapper(String requestId, HttpServletRequest request) {
		super(request);
		this.requestId = requestId;
	}

	@Override
	public String getHeader(String name) {
		if (StringUtils.equalsIgnoreCase(name, WebConstants.HEADER_X_REQUEST_ID)) {
			return requestId;
		}
		return super.getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		List<String> names = Collections.list(super.getHeaderNames());
		names.add(WebConstants.HEADER_X_REQUEST_ID);
		return Collections.enumeration(names);
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		if (StringUtils.equalsIgnoreCase(name, WebConstants.HEADER_X_REQUEST_ID)) {
			return Collections.enumeration(Arrays.asList(requestId));
		}
		return super.getHeaders(name);
	}

}
