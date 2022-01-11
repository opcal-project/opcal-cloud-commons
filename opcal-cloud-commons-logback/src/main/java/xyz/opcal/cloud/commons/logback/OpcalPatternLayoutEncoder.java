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

package xyz.opcal.cloud.commons.logback;

import org.springframework.boot.logging.logback.ColorConverter;
import org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter;
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;

/**
 * A logback pattern layout encoder which will convert custom thread id to the
 * pattern and integrating with Spring's logback converters. Usage in
 * logback.xml
 * 
 * <pre>
 * {@code
 * 	<appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
 *      <encoder class="xyz.opcal.cloud.commons.logback.OpcalPatternLayoutEncoder">
 *          <property name="logPattern" value="%gray(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}) %highlight(${LOG_LEVEL_PATTERN:-%5p}) %yellow(${PID:- }) %gray(---) %magenta([%32.32currentThreadId]) %cyan(%-40.40logger{39}) %gray(:) %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}"/>
 *      </encoder>
 *  </appender>
 * }
 * </pre>
 *
 * @since 0.1.0
 */
public class OpcalPatternLayoutEncoder extends PatternLayoutEncoderBase<ILoggingEvent> {

	@Override
	public void start() {
		PatternLayout.DEFAULT_CONVERTER_MAP.put("clr", ColorConverter.class.getName());
		PatternLayout.DEFAULT_CONVERTER_MAP.put("wex", WhitespaceThrowableProxyConverter.class.getName());
		PatternLayout.DEFAULT_CONVERTER_MAP.put("wEx", ExtendedWhitespaceThrowableProxyConverter.class.getName());
		PatternLayout.DEFAULT_CONVERTER_MAP.put(OpcalLogbackConstants.CURRENT_THREAD_ID, OpcalThreadClassicConverter.class.getName());
		PatternLayout patternLayout = new PatternLayout();
		patternLayout.setContext(context);
		patternLayout.setPattern(getPattern());
		patternLayout.setOutputPatternAsHeader(outputPatternAsHeader);
		patternLayout.start();
		this.layout = patternLayout;
		super.start();
	}

}
