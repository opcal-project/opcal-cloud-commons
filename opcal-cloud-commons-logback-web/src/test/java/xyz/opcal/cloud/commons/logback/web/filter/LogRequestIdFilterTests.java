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

package xyz.opcal.cloud.commons.logback.web.filter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.filter.CompositeFilter;

import xyz.opcal.cloud.commons.logback.OpcalLogbackConstants;
import xyz.opcal.cloud.commons.web.servlet.filter.RequestIdFilter;

class LogRequestIdFilterTests {

	private CompositeFilter filterProxy;

	@BeforeEach
	void setup() {
		filterProxy = new CompositeFilter();
		filterProxy.setFilters(Arrays.asList(new RequestIdFilter(), new LogRequestIdFilter()));
	}

	@Test
	void filter() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest(HttpMethod.GET.name(), "/");
		MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain filterChain = (filterRequest, filterResponse) -> {
			assertNotNull(MDC.get(OpcalLogbackConstants.MDC_THREAD_ID));
		};
		filterProxy.doFilter(request, response, filterChain);
	}

}
