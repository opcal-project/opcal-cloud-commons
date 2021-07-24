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
