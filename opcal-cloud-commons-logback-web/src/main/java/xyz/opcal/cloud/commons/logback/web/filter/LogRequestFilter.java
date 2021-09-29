/*
 * Copyright 2021 Opcal
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
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import xyz.opcal.cloud.commons.logback.web.http.LogRequestWrapper;
import xyz.opcal.cloud.commons.logback.web.http.LogResponseWrapper;
import xyz.opcal.cloud.commons.logback.web.http.PathMatcher;
import xyz.opcal.cloud.commons.logback.web.http.config.LogRequestConfig;
import xyz.opcal.cloud.commons.web.WebConstants;
import xyz.opcal.cloud.commons.web.utils.RequestUtils;

@Slf4j
@Order(-90)
public class LogRequestFilter extends OncePerRequestFilter {

	private static final Logger requestLogger = LoggerFactory.getLogger("requestLogger");
	private static final Logger accessLogger = LoggerFactory.getLogger("accessLogger");

	private @Autowired ObjectMapper objectMapper;
	private @Autowired LogRequestConfig logRequestConfig;
	private PathMatcher disablePathMatcher;

	@PostConstruct
	public void init() {
		String[] disablePaths;
		if (ArrayUtils.isEmpty(logRequestConfig.getDisablePaths())) {
			disablePaths = new String[0];
		} else {
			disablePaths = logRequestConfig.getDisablePaths();
		}
		disablePathMatcher = new PathMatcher(disablePaths);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String contentType = RequestUtils.cleanTaint(request.getHeader(HttpHeaders.CONTENT_TYPE));
		if (logRequestConfig.isDisableMediaType(contentType)) {
			log.debug("request [{}] contentType [{}] will not be logged in this filter.", request.getRequestURI(), contentType);
			return true;
		}
		if (disablePathMatcher.matches(request)) {
			log.debug("request [{}] will not be logged in this filter.", request.getRequestURI());
			return true;
		}
		return false;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();

		String requestId = RequestUtils.getRequestId(request);
		String requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);

		String requestURI = request.getRequestURI();
		String method = request.getMethod();

		String parameters = objectMapper.writeValueAsString(request.getParameterMap());
		requestLogger.info("url [{}] method [{}] request id [{}] request parameter [{}] body [{}]", requestURI, method, requestId, parameters, requestBody);

		String remoteIp = RequestUtils.getIp(request);

		LogRequestWrapper requestWrapper = null;
		LogResponseWrapper responseWrapper = null;
		try {
			requestWrapper = new LogRequestWrapper(requestId, request, requestBody);
			responseWrapper = new LogResponseWrapper(requestId, response);
			responseWrapper.addHeader(WebConstants.HEADER_X_REQUEST_ID, requestId);
			filterChain.doFilter(requestWrapper, responseWrapper);
		} finally {
			long millis = System.currentTimeMillis() - startTime;
			requestLogger.info("url [{}] request id [{}]  response body [{}]", requestURI, requestId, responseWrapper);
			accessLogger.info("remote ip [{}] url [{}] request id [{}] finished in [{}] milliseconds", remoteIp, requestURI, requestId, millis);
		}
	}
}
