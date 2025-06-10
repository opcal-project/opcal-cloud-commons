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

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xyz.opcal.cloud.commons.logback.OpcalLogbackConstants;
import xyz.opcal.cloud.commons.web.servlet.filter.RequestIdFilter;
import xyz.opcal.cloud.commons.web.utils.HttpServletRequestUtils;

public class LogRequestIdFilter extends OncePerRequestFilter {

	/**
	 * Filter Chain after RequestIdFilter, requestId is not null.
	 * 
	 * @see RequestIdFilter
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		MDC.put(OpcalLogbackConstants.MDC_THREAD_ID, HttpServletRequestUtils.getRequestId(request));
		try {
			filterChain.doFilter(request, response);
		} finally {
			MDC.remove(OpcalLogbackConstants.MDC_THREAD_ID);
		}
	}
}
