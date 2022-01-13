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

package xyz.opcal.cloud.commons.logback.web.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.filter.CompositeFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.opcal.cloud.commons.logback.http.config.LogRequestConfig;
import xyz.opcal.cloud.commons.logback.web.http.LogRequestWrapper;
import xyz.opcal.cloud.commons.logback.web.http.LogResponseWrapper;
import xyz.opcal.cloud.commons.web.servlet.filter.RequestIdFilter;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogRequestFilterTests {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	CompositeFilter setup(ObjectMapper objectMapper, LogRequestConfig logRequestConfig) {
		CompositeFilter filterProxy = new CompositeFilter();
		filterProxy.setFilters(Arrays.asList(new RequestIdFilter(), new LogRequestIdFilter(), new LogRequestFilter(objectMapper, logRequestConfig)));
		return filterProxy;
	}

	@Test
	@Order(1)
	void testDisablePaths() throws ServletException, IOException {
		LogRequestConfig logRequestConfig = new LogRequestConfig();
		logRequestConfig.setDisablePaths(new String[] { "/disable/**" });
		MockHttpServletRequest request = new MockHttpServletRequest(HttpMethod.GET.name(), "/disable/index");
		request.setServletPath("/disable");
		request.setPathInfo("/index");

		FilterChain filterChain = (filterRequest, filterResponse) -> {
			assertThat(filterRequest, not(instanceOf(LogRequestWrapper.class)));
			assertThat(filterResponse, not(instanceOf(LogResponseWrapper.class)));
		};
		setup(objectMapper, logRequestConfig).doFilter(request, new MockHttpServletResponse(), filterChain);
	}

	@Test
	@Order(2)
	void testDisableMediaTypes() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest(HttpMethod.GET.name(), "/static/i.png");
		request.setServletPath("/static");
		request.setPathInfo("/i.png");
		request.setContentType(MediaType.IMAGE_PNG_VALUE);

		FilterChain filterChain = (filterRequest, filterResponse) -> {
			assertThat(filterRequest, not(instanceOf(LogRequestWrapper.class)));
			assertThat(filterResponse, not(instanceOf(LogResponseWrapper.class)));
		};
		setup(objectMapper, new LogRequestConfig()).doFilter(request, new MockHttpServletResponse(), filterChain);
	}

	@Test
	@Order(3)
	void testFilter() throws ServletException, IOException {

		Map<String, Object> content = new HashMap<>();
		content.put("name", "apple");
		content.put("quantity", 10);

		MockHttpServletRequest request = new MockHttpServletRequest(HttpMethod.POST.name(), "/api/product?ca=123");
		request.setServletPath("/api");
		request.setPathInfo("/product");
		request.setQueryString("ca=123");
		request.setParameter("ca", "123");
		request.setContentType(MediaType.APPLICATION_JSON_VALUE);
		request.setContent(objectMapper.writeValueAsBytes(content));

		Map<String, Object> result = new HashMap<>();
		result.put("message", "success");
		result.put("code", 200);

		MockHttpServletResponse response = new MockHttpServletResponse();
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		FilterChain filterChain = (filterRequest, filterResponse) -> {
			assertThat(filterRequest, instanceOf(LogRequestWrapper.class));
			assertThat(filterResponse, instanceOf(LogResponseWrapper.class));
			assertNotNull(((LogRequestWrapper) filterRequest).getRequestId());
			assertNotNull(((LogResponseWrapper) filterResponse).getRequestId());

			filterResponse.getOutputStream().write(objectMapper.writeValueAsBytes(result));
		};

		setup(objectMapper, new LogRequestConfig()).doFilter(request, response, filterChain);
	}
}
