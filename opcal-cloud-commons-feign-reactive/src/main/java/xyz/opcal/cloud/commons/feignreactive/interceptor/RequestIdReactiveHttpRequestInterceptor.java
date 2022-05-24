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

package xyz.opcal.cloud.commons.feignreactive.interceptor;

import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import reactivefeign.client.ReactiveHttpRequest;
import reactivefeign.client.ReactiveHttpRequestInterceptor;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;
import xyz.opcal.cloud.commons.web.WebConstants;

public class RequestIdReactiveHttpRequestInterceptor implements ReactiveHttpRequestInterceptor {

	@Override
	public Mono<ReactiveHttpRequest> apply(ReactiveHttpRequest reactiveHttpRequest) {
		return Mono.deferContextual(contextView -> {
			reactiveHttpRequest.headers().put(WebConstants.HEADER_X_REQUEST_ID, Arrays.asList(getRequestId(contextView)));
			return Mono.just(reactiveHttpRequest);
		});
	}

	private String getRequestId(ContextView contextView) {
		String requestId = contextView.getOrDefault(WebConstants.HEADER_X_REQUEST_ID, "");
		if (StringUtils.isNotBlank(requestId)) {
			return requestId;
		}
		return UUID.randomUUID().toString().replace("-", "");
	}
}
