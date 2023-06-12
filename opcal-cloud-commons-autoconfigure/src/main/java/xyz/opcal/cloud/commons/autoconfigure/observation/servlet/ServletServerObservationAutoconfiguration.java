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

package xyz.opcal.cloud.commons.autoconfigure.observation.servlet;

import org.springframework.boot.actuate.autoconfigure.observation.web.servlet.WebMvcObservationAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import xyz.opcal.cloud.commons.web.observation.servlet.ServletRequestObservationConvention;
import xyz.opcal.cloud.commons.web.servlet.filter.RequestIdFilter;

@AutoConfiguration(after = { WebMvcObservationAutoConfiguration.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({ DispatcherServlet.class, Observation.class })
@ConditionalOnBean(ObservationRegistry.class)
public class ServletServerObservationAutoconfiguration {

	@Bean
	@ConditionalOnBean(RequestIdFilter.class)
	ServletRequestObservationConvention servletRequestObservationConvention() {
		return new ServletRequestObservationConvention();
	}
}
