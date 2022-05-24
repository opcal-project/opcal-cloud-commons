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

package xyz.opcal.cloud.commons.web.reactive.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.handler.FilteringWebHandler;

import lombok.Getter;
import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.web.WebConstants;

class ReactiveRequestIdFilterTests {

	@Test
	void requestIdFilter() {
		RequestIdWebHandler targetHandler = new RequestIdWebHandler();
		List<WebFilter> filters = Arrays.asList(new ReactiveRequestIdFilter(), //
				(exchange, chain) -> chain.filter(exchange).contextWrite(context -> {
					assertNotNull(context.get(WebConstants.HEADER_X_REQUEST_ID));
					return context;
				}));
		new FilteringWebHandler(targetHandler, filters) //
				.handle(MockServerWebExchange.from(MockServerHttpRequest.get("/"))) //
				.block(Duration.ZERO);
		assertNotNull(targetHandler.getRequestId());
	}

	@Test
	void requestIdFromClient() {
		String requestId = UUID.randomUUID().toString().replace("-", "");
		RequestIdWebHandler targetHandler = new RequestIdWebHandler();
		new FilteringWebHandler(targetHandler, Collections.singletonList(new ReactiveRequestIdFilter())) //
				.handle(MockServerWebExchange.from( //
						MockServerHttpRequest //
								.get("/") //
								.header(WebConstants.HEADER_X_REQUEST_ID, requestId)) //
				).block(Duration.ZERO);
		assertNotNull(targetHandler.getRequestId());
		assertEquals(requestId, targetHandler.getRequestId());
	}

	private static class RequestIdWebHandler implements WebHandler {

		@Getter
		private String requestId;

		@Override
		public Mono<Void> handle(ServerWebExchange exchange) {
			requestId = exchange.getRequest().getHeaders().getFirst(WebConstants.HEADER_X_REQUEST_ID);
			return Mono.empty();
		}
	}
}
