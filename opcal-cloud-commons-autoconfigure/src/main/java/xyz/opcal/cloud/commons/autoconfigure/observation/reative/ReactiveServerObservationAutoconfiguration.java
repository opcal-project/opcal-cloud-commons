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

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import io.micrometer.observation.Observation;
import xyz.opcal.cloud.commons.web.configuration.ReactiveRequestIdConfiguration;
import xyz.opcal.cloud.commons.web.observation.reactive.ReactiveRequestObservationConvention;

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(Observation.class)
@Import(ReactiveRequestIdConfiguration.class)
public class ReactiveServerObservationAutoconfiguration {

	@Bean
	@ConditionalOnMissingBean
	ReactiveRequestObservationConvention reactiveRequestObservationConvention() {
		return new ReactiveRequestObservationConvention();
	}
}
