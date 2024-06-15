/*
 * Copyright 2020-2024 Opcal
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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;

import xyz.opcal.cloud.commons.logback.webflux.filter.LogWebfluxRequestFilter;
import xyz.opcal.cloud.commons.logback.webflux.filter.LogWebfluxRequestIdFilter;
import xyz.opcal.cloud.commons.logback.webflux.filter.MDCCleanFilter;
import xyz.opcal.cloud.commons.web.reactive.filter.ReactiveRequestIdFilter;

class ReactiveWebLogConfigurationTest {


	static ApplicationContextRunner contextRunner() {
		return new ApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(ReactiveWebLogConfiguration.class, JacksonAutoConfiguration.class, RefreshAutoConfiguration.class));
	}

	@Test
	void enableLogRequest() {
		contextRunner()
				.withPropertyValues("opcal.cloud.log.reactive.request=true")
				.run(context -> {
					assertThat(context).hasSingleBean(ReactiveRequestIdFilter.class);
					assertThat(context).hasSingleBean(LogWebfluxRequestIdFilter.class);
					assertThat(context).hasSingleBean(MDCCleanFilter.class);
					assertThat(context).hasSingleBean(LogWebfluxRequestFilter.class);
				});
	}

	@Test
	void enableLogRequestId() {
		contextRunner()
				.withPropertyValues("opcal.cloud.log.reactive.request-id=true")
				.run(context -> {
					assertThat(context).hasSingleBean(ReactiveRequestIdFilter.class);
					assertThat(context).hasSingleBean(LogWebfluxRequestIdFilter.class);
					assertThat(context).hasSingleBean(MDCCleanFilter.class);
					assertThat(context).doesNotHaveBean(LogWebfluxRequestFilter.class);
				});
	}

}