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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest.BaseBuilder;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.handler.FilteringWebHandler;

import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;
import xyz.opcal.cloud.commons.logback.http.config.LogRequestConfig;
import xyz.opcal.cloud.commons.logback.webflux.http.LogRequestDecorator;
import xyz.opcal.cloud.commons.logback.webflux.http.LogResponseDecorator;
import xyz.opcal.cloud.commons.web.reactive.filter.ReactiveRequestIdFilter;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogWebfluxRequestFilterTests {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@Order(1)
	void testDisablePaths() {
		LogRequestConfig logWebfluxConfig = new LogRequestConfig();
		logWebfluxConfig.setDisablePaths(new String[] { "/disable/**" });
		LogRequestWebHandler targetHandler = new LogRequestWebHandler();
		BaseBuilder<?> requestBuilder = MockServerHttpRequest.get("/disable/index");
		new FilteringWebHandler(targetHandler,
				Arrays.asList(new ReactiveRequestIdFilter(), new LogWebfluxRequestIdFilter(), new LogWebfluxRequestFilter(logWebfluxConfig)))
				.handle(MockServerWebExchange.from(requestBuilder)) //
				.block(Duration.ZERO);

		assertThat(targetHandler.getRequest(), not(instanceOf(LogRequestDecorator.class)));
		assertThat(targetHandler.getResponse(), not(instanceOf(LogResponseDecorator.class)));
	}

	@Test
	@Order(2)
	void testDisableMediaTypes() {
		LogRequestConfig logWebfluxConfig = new LogRequestConfig();
		LogRequestWebHandler targetHandler = new LogRequestWebHandler();
		BaseBuilder<?> requestBuilder = MockServerHttpRequest.get("/static/a.pdf").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
		new FilteringWebHandler(targetHandler,
				Arrays.asList(new ReactiveRequestIdFilter(), new LogWebfluxRequestIdFilter(), new LogWebfluxRequestFilter(logWebfluxConfig)))
				.handle(MockServerWebExchange.from(requestBuilder)) //
				.block(Duration.ZERO);

		assertThat(targetHandler.getRequest(), not(instanceOf(LogRequestDecorator.class)));
		assertThat(targetHandler.getResponse(), not(instanceOf(LogResponseDecorator.class)));
	}

	@Test
	@Order(3)
	void testFilter() {
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("name", "apple");
		requestBody.put("quantity", 10);

		LogRequestWebHandler targetHandler = new LogRequestWebHandler();
		String body = objectMapper.writeValueAsString(requestBody);
		MockServerHttpRequest request = MockServerHttpRequest.post("/api/product?ca=456") //
				.contentType(MediaType.APPLICATION_JSON).body(body);

		new FilteringWebHandler(targetHandler,
				Arrays.asList(new ReactiveRequestIdFilter(), new LogWebfluxRequestIdFilter(), new LogWebfluxRequestFilter(new LogRequestConfig())))
				.handle(MockServerWebExchange.from(request)) //
				.block(Duration.ZERO);

		assertThat(targetHandler.getRequest(), instanceOf(LogRequestDecorator.class));
		assertThat(targetHandler.getResponse(), instanceOf(LogResponseDecorator.class));
	}

	private static class LogRequestWebHandler implements WebHandler {

		@Getter
		private ServerHttpRequest request;

		@Getter
		private ServerHttpResponse response;

		@Override
		public Mono<Void> handle(ServerWebExchange exchange) {
			request = exchange.getRequest();
			response = exchange.getResponse();
			DataBuffer dataBuffer = request.getBody().blockFirst();
			if (Objects.nonNull(dataBuffer)) {
				dataBuffer.toString(Charset.defaultCharset());// mock doing something
				response.setStatusCode(HttpStatus.OK);
				response.writeAndFlushWith(Flux.just(Flux.just(response.bufferFactory().wrap("ok".getBytes(StandardCharsets.UTF_8)))));
				return response.writeWith(Flux.just(response.bufferFactory().wrap("ok".getBytes(StandardCharsets.UTF_8))));
			}
			return Mono.empty();
		}
	}
}
