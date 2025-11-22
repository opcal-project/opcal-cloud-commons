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

package xyz.opcal.cloud.commons.autoconfigure.logback;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import xyz.opcal.cloud.commons.logback.webflux.configuration.LogWebfluxRequestConfiguration;
import xyz.opcal.cloud.commons.logback.webflux.configuration.LogWebfluxRequestIdConfiguration;
import xyz.opcal.cloud.commons.web.configuration.ReactiveRequestIdConfiguration;

@AutoConfiguration
public final class ReactiveWebLogConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(value = "opcal.cloud.log.reactive.request-id", havingValue = "true")
	@Import(value = { ReactiveRequestIdConfiguration.class, LogWebfluxRequestIdConfiguration.class })
	static class AutoLogWebfluxRequestIdConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(value = "opcal.cloud.log.reactive.request", havingValue = "true")
	@Import(value = { ReactiveRequestIdConfiguration.class, LogWebfluxRequestIdConfiguration.class, LogWebfluxRequestConfiguration.class })
	static class AutoLogWebfluxRequestConfiguration {

	}
}
