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

package xyz.opcal.cloud.commons.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.integration.feign.HttpbinFeign;
import xyz.opcal.cloud.commons.integration.feign.ReactiveHttpbinFeign;
import xyz.opcal.cloud.commons.web.WebConstants;

@Slf4j
@RestController
@RequestMapping("/httpbin")
public class ReHttpbinController {

	private @Autowired HttpbinFeign httpbinFeign;
	private @Autowired ReactiveHttpbinFeign reactiveHttpbinFeign;

	@GetMapping("/ip")
	public Mono<String> httpbinIp() {
		return Mono.deferContextual(contextView -> {
			String requestId = contextView.getOrDefault(WebConstants.HEADER_X_REQUEST_ID, "");
			return WebClient.builder().baseUrl("https://httpbin.org").filter(logHeader()).build().get().uri("/ip")
					.header(WebConstants.HEADER_X_REQUEST_ID, requestId).retrieve().bodyToMono(String.class);
		});
	}

	@GetMapping("/feign/ip")
	public Mono<String> feignIp() {
		return Mono.just(httpbinFeign.ip());
	}

	@GetMapping("/reactivefeign/ip")
	public Mono<String> reactiveFeignIp() {
		return reactiveHttpbinFeign.ip();
	}

	private ExchangeFilterFunction logHeader() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("header logs \n{}={}", name, value)));
			return Mono.just(clientRequest);
		});
	}
}
