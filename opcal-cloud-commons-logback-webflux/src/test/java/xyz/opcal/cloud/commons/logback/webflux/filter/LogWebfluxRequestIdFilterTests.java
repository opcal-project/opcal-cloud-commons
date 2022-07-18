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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.handler.FilteringWebHandler;

import lombok.Getter;
import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.logback.OpcalLogbackConstants;
import xyz.opcal.cloud.commons.web.reactive.filter.ReactiveRequestIdFilter;

class LogWebfluxRequestIdFilterTests {

	@Test
	void requestIdFilter() {
		LogRequestIdWebHandler targetHandler = new LogRequestIdWebHandler();
		new FilteringWebHandler(targetHandler, Arrays.asList(new ReactiveRequestIdFilter(), new LogWebfluxRequestIdFilter()))
				.handle(MockServerWebExchange.from(MockServerHttpRequest.get("/"))) //
				.block(Duration.ZERO);
		assertNotNull(targetHandler.getMdcThreadId());
	}

	private static class LogRequestIdWebHandler implements WebHandler {

		@Getter
		private String mdcThreadId;

		@Override
		public Mono<Void> handle(ServerWebExchange exchange) {
			mdcThreadId = MDC.get(OpcalLogbackConstants.MDC_THREAD_ID);
			return Mono.empty();
		}
	}
}
