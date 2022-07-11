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

package xyz.opcal.cloud.commons.autoconfigure.feign;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import xyz.opcal.cloud.commons.autoconfigure.web.ReactiveWebRequestConfiguration;
import xyz.opcal.cloud.commons.autoconfigure.web.ServletWebRequestConfiguration;
import xyz.opcal.cloud.commons.feign.configuration.FeignRequestIdConfiguration;
import xyz.opcal.cloud.commons.feign.http.RequestThreadContextIdHandler;

@AutoConfiguration(after = { ReactiveWebRequestConfiguration.class, ServletWebRequestConfiguration.class })
public class FeignAutoConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(value = "opcal.cloud.feign.request-id", havingValue = "true")
	@Import(FeignRequestIdConfiguration.class)
	static class AutoFeignRequestIdConfiguration {

		@Bean
		@ConditionalOnMissingBean
		@ConditionalOnProperty(value = "opcal.cloud.web.reactive.request-id", havingValue = "true")
		public RequestThreadContextIdHandler requestThreadContextIdHandler() {
			return new RequestThreadContextIdHandler();
		}
	}

}
