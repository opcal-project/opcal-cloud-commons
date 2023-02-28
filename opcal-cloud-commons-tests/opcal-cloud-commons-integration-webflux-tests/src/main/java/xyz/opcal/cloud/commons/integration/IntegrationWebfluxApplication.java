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

package xyz.opcal.cloud.commons.integration;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;

import reactivefeign.client.ReactiveHttpRequest;
import reactivefeign.client.ReactiveHttpRequestInterceptor;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.autoconfigure.annotation.EnableOpcalCloud;

@EnableOpcalCloud
@Configuration
@SpringBootApplication
@EnableFeignClients
@EnableReactiveFeignClients
public class IntegrationWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrationWebfluxApplication.class, args);
	}

	@Bean
	@ConditionalOnMissingBean
	public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
		return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
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
