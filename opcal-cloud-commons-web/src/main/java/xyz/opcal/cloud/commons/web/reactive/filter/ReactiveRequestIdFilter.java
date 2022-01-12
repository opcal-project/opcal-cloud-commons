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

package xyz.opcal.cloud.commons.web.reactive.filter;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.web.WebConstants;
import xyz.opcal.cloud.commons.web.utils.ServerHttpRequestUtils;

@Order(-100)
public class ReactiveRequestIdFilter implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String requestId = ServerHttpRequestUtils.getRequestId(exchange.getRequest());
		ServerWebExchange chainExchange = exchange;
		if (StringUtils.isBlank(requestId)) {
			String newReqId = UUID.randomUUID().toString().replace("-", "");
			chainExchange = exchange.mutate().request( //
					exchange.getRequest().mutate() //
							.headers(httpHeaders -> httpHeaders.add(WebConstants.HEADER_X_REQUEST_ID, newReqId)) //
							.build() //
			).build();
		}
		return chain.filter(chainExchange);
	}

}
