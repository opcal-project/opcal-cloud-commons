/*
 * Copyright 2020-2023 Opcal
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

package xyz.opcal.cloud.commons.autoconfigure.observation.reative;

import org.springframework.boot.actuate.autoconfigure.observation.web.reactive.WebFluxObservationAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import xyz.opcal.cloud.commons.web.configuration.ReactiveRequestIdConfiguration;
import xyz.opcal.cloud.commons.web.observation.reactive.ReactiveRequestObservationConvention;
import xyz.opcal.cloud.commons.web.reactive.filter.ReactiveRequestIdFilter;

@AutoConfiguration(after = { WebFluxObservationAutoConfiguration.class, ReactiveRequestIdConfiguration.class })
@ConditionalOnClass(Observation.class)
@ConditionalOnBean(ObservationRegistry.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveServerObservationAutoconfiguration {

	@Bean
	@ConditionalOnBean(ReactiveRequestIdFilter.class)
	ReactiveRequestObservationConvention reactiveRequestObservationConvention() {
		return new ReactiveRequestObservationConvention();
	}
}
