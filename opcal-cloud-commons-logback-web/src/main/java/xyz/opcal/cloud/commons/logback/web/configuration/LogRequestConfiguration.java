package xyz.opcal.cloud.commons.logback.web.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.opcal.cloud.commons.logback.web.filter.LogRequestFilter;
import xyz.opcal.cloud.commons.logback.web.http.config.LogRequestConfig;

@Configuration
@EnableConfigurationProperties(value = {LogRequestConfig.class})
public class LogRequestConfiguration {

	@Bean
	LogRequestFilter logRequestFilter() {
		return new LogRequestFilter();
	}

}
