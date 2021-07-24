package xyz.opcal.cloud.commons.logback.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import xyz.opcal.cloud.commons.logback.web.configuration.LogRequestConfiguration;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableRequestId
@Import(LogRequestConfiguration.class)
public @interface EnableLogRequest {

}
