package xyz.opcal.cloud.commons.logback.web.filter;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.opcal.cloud.commons.logback.OpcalLogbackConstants;
import xyz.opcal.cloud.commons.logback.web.LogWebConstants;
import xyz.opcal.cloud.commons.logback.web.utils.RequestUtils;

@Order(-99)
public class RequestIdFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String requestId = RequestUtils.getRequestId(request);
		if (StringUtils.isBlank(requestId)) {
			requestId = UUID.randomUUID().toString().replace("-", "");
		}
		MDC.put(OpcalLogbackConstants.MDC_THREAD_ID, requestId);
		try {
			filterChain.doFilter(request, response);
		} finally {
			MDC.clear();
		}
	}
}
