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

import org.slf4j.MDC;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.logback.OpcalLogbackConstants;

public class MDCCleanFilter implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

		return Mono
				.deferContextual(contextView -> chain.filter(exchange).then(
						Mono.<Void>fromRunnable(() -> MDC.put(OpcalLogbackConstants.MDC_THREAD_ID, contextView.get(OpcalLogbackConstants.MDC_THREAD_ID)))))
				.doFinally(s -> MDC.remove(OpcalLogbackConstants.MDC_THREAD_ID));

	}
}
