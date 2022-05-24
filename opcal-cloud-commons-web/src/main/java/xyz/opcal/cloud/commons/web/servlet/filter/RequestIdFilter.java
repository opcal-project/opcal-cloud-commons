/*
 * Copyright 2021-2021 Opcal
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

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import xyz.opcal.cloud.commons.web.WebConstants;
import xyz.opcal.cloud.commons.web.servlet.http.IdRequestWrapper;
import xyz.opcal.cloud.commons.web.utils.HttpServletRequestUtils;

@Order(-100)
public class RequestIdFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String requestId = HttpServletRequestUtils.getRequestId(request);
		HttpServletRequest chainRequest = request;
		if (StringUtils.isBlank(requestId)) {
			requestId = UUID.randomUUID().toString().replace("-", "");
			chainRequest = new IdRequestWrapper(requestId, request);
		}
		request.setAttribute(WebConstants.HEADER_X_REQUEST_ID, requestId);
		filterChain.doFilter(chainRequest, response);
	}
}
