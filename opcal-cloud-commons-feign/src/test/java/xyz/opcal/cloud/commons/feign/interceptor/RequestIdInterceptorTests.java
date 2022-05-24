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

package xyz.opcal.cloud.commons.feign.interceptor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestTemplate;
import xyz.opcal.cloud.commons.feign.http.RequestThreadContextIdHandler;
import xyz.opcal.cloud.commons.feign.http.ServletRequestIdHandler;
import xyz.opcal.cloud.commons.web.WebConstants;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RequestIdInterceptorTests {

	@Test
	@Order(0)
	void testDefaultNull() {
		RequestIdInterceptor requestIdInterceptor = new RequestIdInterceptor(null);
		RequestTemplate template = new RequestTemplate();
		requestIdInterceptor.apply(template);
		assertNotNull(template.headers().get(WebConstants.HEADER_X_REQUEST_ID));
	}

	@Test
	@Order(1)
	void testDefault() {
		RequestIdInterceptor requestIdInterceptor = new RequestIdInterceptor(Arrays.asList(new ServletRequestIdHandler()));
		RequestTemplate template = new RequestTemplate();
		requestIdInterceptor.apply(template);
		assertNotNull(template.headers().get(WebConstants.HEADER_X_REQUEST_ID));
	}

	@Test
	@Order(2)
	void testDefaultList() {
		RequestIdInterceptor requestIdInterceptor = new RequestIdInterceptor(Arrays.asList(new ServletRequestIdHandler(), new RequestThreadContextIdHandler()));
		RequestTemplate template = new RequestTemplate();
		requestIdInterceptor.apply(template);
		assertNotNull(template.headers().get(WebConstants.HEADER_X_REQUEST_ID));
	}

	@Test
	@Order(3)
	void testWithHolder() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setScheme("http");
		request.setServerName("localhost");
		request.setServerPort(80);
		request.addHeader(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		RequestContextHolder.getRequestAttributes().setAttribute(WebConstants.HEADER_X_REQUEST_ID, request.getHeader(WebConstants.HEADER_X_REQUEST_ID),
				RequestAttributes.SCOPE_REQUEST);
		ServletRequestIdHandler servletRequestIdHandler = new ServletRequestIdHandler();
		RequestIdInterceptor requestIdInterceptor = new RequestIdInterceptor(Arrays.asList(servletRequestIdHandler));
		RequestTemplate template = new RequestTemplate();
		requestIdInterceptor.apply(template);
		assertNotNull(template.headers().get(WebConstants.HEADER_X_REQUEST_ID));
	}

}