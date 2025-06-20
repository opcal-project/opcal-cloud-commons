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

package xyz.opcal.cloud.commons.web.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import jakarta.servlet.DispatcherType;
import xyz.opcal.cloud.commons.web.servlet.filter.RequestIdFilter;

public class RequestIdConfiguration {

	@Bean("requestIdFilter")
	@ConditionalOnMissingBean
	public FilterRegistrationBean<RequestIdFilter> requestIdFilterRegistration() {
		FilterRegistrationBean<RequestIdFilter> registration = new FilterRegistrationBean<>(new RequestIdFilter());
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR);
		return registration;
	}
}
