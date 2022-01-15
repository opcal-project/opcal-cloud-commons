/*
 * Copyright 2022 Opcal
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

package xyz.opcal.cloud.commons.web.servlet.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import xyz.opcal.cloud.commons.web.WebConstants;
import xyz.opcal.cloud.commons.web.servlet.http.IdRequestWrapper;

class RequestIdFilterTests {

	private RequestIdFilter requestIdFilter = new RequestIdFilter();

	private MockHttpServletRequest request;

	private MockFilterChain filterChain;

	@BeforeEach
	void setup() {
		this.request = new MockHttpServletRequest();
		this.request.setScheme("http");
		this.request.setServerName("localhost");
		this.request.setServerPort(80);
		this.filterChain = new MockFilterChain(new HttpServlet() {
		});
	}

	@Test
	void doFilterInternal() throws ServletException, IOException {
		this.requestIdFilter.doFilter(this.request, new MockHttpServletResponse(), this.filterChain);
		assertThat(filterChain.getRequest(), instanceOf(IdRequestWrapper.class));
		assertTrue(StringUtils.isNotBlank(((IdRequestWrapper) filterChain.getRequest()).getRequestId()));
	}

	@Test
	void requestIdFromClient() throws ServletException, IOException {
		this.request.addHeader(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
		this.requestIdFilter.doFilter(this.request, new MockHttpServletResponse(), this.filterChain);
		assertThat(filterChain.getRequest(), not(instanceOf(IdRequestWrapper.class)));
	}
}