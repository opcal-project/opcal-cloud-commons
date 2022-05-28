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

package xyz.opcal.cloud.commons.autoconfigure.logback;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import xyz.opcal.cloud.commons.logback.web.configuration.LogRequestConfiguration;
import xyz.opcal.cloud.commons.logback.web.configuration.LogRequestIdConfiguration;
import xyz.opcal.cloud.commons.web.configuration.RequestIdConfiguration;

@Configuration(proxyBeanMethods = false)
public class ServletWebLogConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(value = "opcal.cloud.log.servlet.request-id", havingValue = "true")
	@Import(value = { RequestIdConfiguration.class, LogRequestIdConfiguration.class })
	static class AutoLogRequestIdConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(value = "opcal.cloud.log.servlet.request", havingValue = "true")
	@Import(value = { RequestIdConfiguration.class, LogRequestIdConfiguration.class, LogRequestConfiguration.class })
	static class AutoLogRequestConfiguration {

	}

}