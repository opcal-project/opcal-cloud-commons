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

package xyz.opcal.cloud.commons.feignreactive;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactivefeign.client.ReactiveHttpRequest;
import reactivefeign.client.ReactiveHttpRequestInterceptor;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.feignreactive.annotation.ReactiveFeignRequestId;
import xyz.opcal.cloud.commons.feignreactive.client.HttpBinClient;
import xyz.opcal.cloud.commons.web.WebConstants;
import xyz.opcal.cloud.commons.web.annotation.EnableReactiveRequestId;

@RestController
@ReactiveFeignRequestId
@EnableReactiveRequestId
@EnableReactiveFeignClients
@SpringBootApplication
public class FeignReactiveTestApplication {

	private @Autowired HttpBinClient httpBinClient;

	public static void main(String[] args) {
		SpringApplication.run(FeignReactiveTestApplication.class, args);
	}

	@GetMapping("/headers")
	public Mono<String> headers() {
		return httpBinClient.headers();
	}

	@GetMapping("/empty/headers")
	public Mono<String> emptyHeaders() {
		return httpBinClient.headers().contextWrite(context -> context.delete(WebConstants.HEADER_X_REQUEST_ID));
	}

	@Component
	static class CiTokenReactiveInterceptor implements ReactiveHttpRequestInterceptor {

		@Override
		public Mono<ReactiveHttpRequest> apply(ReactiveHttpRequest reactiveHttpRequest) {
			return Mono.deferContextual(contextView -> {
				reactiveHttpRequest.headers().put("X-CI-TOKEN", Arrays.asList(System.getenv("CI_TOKEN")));
				return Mono.just(reactiveHttpRequest);
			});
		}
	}
}
