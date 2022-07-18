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

package xyz.opcal.cloud.commons.feign;

import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.feign.client.HttpBinFeign;

@RestController
@EnableFeignClients(clients = HttpBinFeign.class)
@SpringBootApplication
public class FeignTestApplication {

	private @Autowired HttpBinFeign httpBinFeign;

	public static void main(String[] args) {
		SpringApplication.run(FeignTestApplication.class, args);
	}

	@GetMapping("/headers")
	public Mono<String> headers() {
		return Mono.just(httpBinFeign.headers());
	}

	@Bean
	@ConditionalOnMissingBean
	public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
		return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
	}
}
