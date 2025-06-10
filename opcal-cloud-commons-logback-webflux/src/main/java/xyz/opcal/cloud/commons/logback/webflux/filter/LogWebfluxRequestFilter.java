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

package xyz.opcal.cloud.commons.logback.webflux.filter;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.logback.http.config.LogRequestConfig;
import xyz.opcal.cloud.commons.logback.webflux.http.LogRequestDecorator;
import xyz.opcal.cloud.commons.logback.webflux.http.LogResponseDecorator;
import xyz.opcal.cloud.commons.web.utils.ServerHttpRequestUtils;

@Slf4j
public class LogWebfluxRequestFilter implements WebFilter {

	private static final Logger requestLogger = LoggerFactory.getLogger("requestLogger");
	private static final Logger accessLogger = LoggerFactory.getLogger("accessLogger");

	private final AntPathMatcher pathMatcher = new AntPathMatcher();
	private final LogRequestConfig logWebfluxConfig;
	private String[] disablePaths = new String[0];

	public LogWebfluxRequestFilter(LogRequestConfig logWebfluxConfig) {
		this.logWebfluxConfig = logWebfluxConfig;
		init();
	}

	public void init() {
		if (ArrayUtils.isNotEmpty(logWebfluxConfig.getDisablePaths())) {
			disablePaths = logWebfluxConfig.getDisablePaths();
		}
	}

	boolean notFilter(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		String contentType = ServerHttpRequestUtils.cleanHeaderTaint(request, HttpHeaders.CONTENT_TYPE);
		if (logWebfluxConfig.isDisableMediaType(contentType)) {
			log.debug("request [{}] contentType [{}] will not be logged in this filter.", request.getPath().value(), contentType);
			return true;
		}
		if (Arrays.stream(disablePaths).anyMatch(pattern -> pathMatcher.match(pattern, request.getPath().value()))) {
			log.debug("request [{}] will not be logged in this filter.", request.getPath().value());
			return true;
		}
		return false;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		if (notFilter(exchange)) {
			return chain.filter(exchange);
		}
		long startTime = System.currentTimeMillis();
		ServerHttpRequest request = exchange.getRequest();
		String requestId = ServerHttpRequestUtils.getRequestId(request);
		String requestURI = request.getURI().getPath();
		String remoteIp = ServerHttpRequestUtils.getIp(request);
		String method = Optional.ofNullable(request.getMethod()).orElse(HttpMethod.GET).name();
		String parameters = request.getURI().getQuery();

		final LogRequestDecorator logRequestDecorator = new LogRequestDecorator(request);
		final LogResponseDecorator logResponseDecorator = new LogResponseDecorator(exchange.getResponse());

		ServerWebExchangeDecorator exchangeDecorator = new ServerWebExchangeDecorator(exchange) {

			@Override
			public ServerHttpRequest getRequest() {
				return logRequestDecorator;
			}

			@Override
			public ServerHttpResponse getResponse() {
				return logResponseDecorator;
			}
		};
		return chain.filter(exchangeDecorator).doFinally(signalType -> {
			long millis = System.currentTimeMillis() - startTime;
			requestLogger.info("url [{}] method [{}] request id [{}] request parameter [{}] body [{}]", requestURI, method, requestId, parameters,
					logRequestDecorator);
			requestLogger.info("url [{}] request id [{}] status [{}] response body [{}]", requestURI, requestId, logResponseDecorator.getStatusCode(),
					logResponseDecorator);
			accessLogger.info("remote ip [{}] url [{}] request id [{}] finished in [{}] milliseconds", remoteIp, requestURI, requestId, millis);
			logRequestDecorator.close();
			logResponseDecorator.close();
		});
	}
}
