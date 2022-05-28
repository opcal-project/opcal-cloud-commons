/*
 * Copyright 2021 Opcal
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

package xyz.opcal.cloud.commons.logback.webflux.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

import xyz.opcal.cloud.commons.logback.http.config.LogRequestConfig;
import xyz.opcal.cloud.commons.logback.webflux.filter.LogWebfluxRequestFilter;

public class LogWebfluxRequestConfiguration {

	@Bean
	@RefreshScope
	@ConfigurationProperties("opcal.cloud.log.webflux")
	public LogRequestConfig logWebfluxConfig() {
		return new LogRequestConfig();
	}

	@Bean
	@ConditionalOnMissingBean
	public LogWebfluxRequestFilter logWebfluxRequestFilter(LogRequestConfig logWebfluxConfig) {
		return new LogWebfluxRequestFilter(logWebfluxConfig);
	}
}
