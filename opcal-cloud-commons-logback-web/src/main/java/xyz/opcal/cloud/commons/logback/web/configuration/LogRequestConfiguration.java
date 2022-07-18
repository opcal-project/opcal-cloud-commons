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

package xyz.opcal.cloud.commons.logback.web.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.opcal.cloud.commons.logback.http.config.LogRequestConfig;
import xyz.opcal.cloud.commons.logback.web.filter.LogRequestFilter;

public class LogRequestConfiguration {

	@Bean
	@RefreshScope
	@ConfigurationProperties("opcal.cloud.log.web")
	public LogRequestConfig logRequestConfig() {
		return new LogRequestConfig();
	}

	@Bean
	@ConditionalOnMissingBean
	public LogRequestFilter logRequestFilter(ObjectMapper objectMapper, LogRequestConfig logRequestConfig) {
		return new LogRequestFilter(objectMapper, logRequestConfig);
	}

}
