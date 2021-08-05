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

package xyz.opcal.cloud.commons.logback.web.http;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.springframework.util.AntPathMatcher;

public class PathMatcher {

	private final String[] patterns;

	private final AntPathMatcher antPathMathcer;

	public PathMatcher(@NotNull String[] patterns) {
		this.patterns = patterns;
		this.antPathMathcer = new AntPathMatcher();
	}

	private String getRequestPath(HttpServletRequest request) {
		String url = request.getServletPath();
		if (request.getPathInfo() != null) {
			url += request.getPathInfo();
		}
		return url;
	}

	public boolean matches(HttpServletRequest request) {
		String requestPath = getRequestPath(request);
		for (String pattern : patterns) {
			if (antPathMathcer.match(pattern, requestPath)) {
				return true;
			}
		}
		return false;
	}
}
