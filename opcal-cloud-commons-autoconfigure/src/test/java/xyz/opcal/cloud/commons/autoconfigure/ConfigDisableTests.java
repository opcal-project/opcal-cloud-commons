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

package xyz.opcal.cloud.commons.autoconfigure;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import xyz.opcal.cloud.commons.feign.http.RequestThreadContextIdHandler;
import xyz.opcal.cloud.commons.feign.http.ServletRequestIdHandler;
import xyz.opcal.cloud.commons.feign.interceptor.RequestIdInterceptor;
import xyz.opcal.cloud.commons.feignreactive.interceptor.RequestIdReactiveHttpRequestInterceptor;
import xyz.opcal.cloud.commons.logback.web.filter.LogRequestFilter;
import xyz.opcal.cloud.commons.logback.web.filter.LogRequestIdFilter;
import xyz.opcal.cloud.commons.logback.webflux.filter.LogWebfluxRequestFilter;
import xyz.opcal.cloud.commons.logback.webflux.filter.LogWebfluxRequestIdFilter;
import xyz.opcal.cloud.commons.logback.webflux.filter.MDCCleanFilter;
import xyz.opcal.cloud.commons.web.reactive.filter.ReactiveRequestIdFilter;
import xyz.opcal.cloud.commons.web.servlet.filter.RequestIdFilter;

@SpringBootTest(classes = AutoConfigTestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConfigDisableTests {

	@Test
	@Order(0)
	void disableRequestId(ApplicationContext applicationContext) {
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(RequestIdFilter.class));
	}

	@Test
	@Order(1)
	void disableReactiveRequestId(ApplicationContext applicationContext) {
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(ReactiveRequestIdFilter.class));
	}

	@Test
	@Order(2)
	void disableLogRequestId(ApplicationContext applicationContext) {
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(RequestIdFilter.class));
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(LogRequestIdFilter.class));
	}

	@Test
	@Order(3)
	void disableLogRequest(ApplicationContext applicationContext) {
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(RequestIdFilter.class));
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(LogRequestIdFilter.class));
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(LogRequestFilter.class));
	}

	@Test
	@Order(4)
	void disableLogReactiveRequestId(ApplicationContext applicationContext) {
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(ReactiveRequestIdFilter.class));
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(LogWebfluxRequestIdFilter.class));
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(MDCCleanFilter.class));
	}

	@Test
	@Order(5)
	void disableLogReactiveRequest(ApplicationContext applicationContext) {
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(ReactiveRequestIdFilter.class));
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(LogWebfluxRequestIdFilter.class));
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(MDCCleanFilter.class));
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(LogWebfluxRequestFilter.class));
	}

	@Test
	@Order(6)
	void disableFeign(ApplicationContext applicationContext) {
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(ServletRequestIdHandler.class));
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(RequestIdInterceptor.class));
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(RequestThreadContextIdHandler.class));
	}

	@Test
	@Order(7)
	void disableFeignreactive(ApplicationContext applicationContext) {
		assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(RequestIdReactiveHttpRequestInterceptor.class));
	}
}
