package xyz.opcal.cloud.commons.logback.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.opcal.cloud.commons.logback.web.filter.RequestIdFilter;

@Configuration
public class RequestIdConfiguration {

    @Bean
    public RequestIdFilter requestIdFilter() {
        return new RequestIdFilter();
    }

}
