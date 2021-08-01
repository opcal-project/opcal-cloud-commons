package xyz.opcal.cloud.commons.logback;

import org.springframework.boot.logging.logback.ColorConverter;
import org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter;
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;

public class OpcalPatternLayoutEncoder extends PatternLayoutEncoderBase<ILoggingEvent> {

	@Override
	public void start() {
		PatternLayout.defaultConverterMap.put("clr", ColorConverter.class.getName());
		PatternLayout.defaultConverterMap.put("wex", WhitespaceThrowableProxyConverter.class.getName());
		PatternLayout.defaultConverterMap.put("wEx", ExtendedWhitespaceThrowableProxyConverter.class.getName());
		PatternLayout.defaultConverterMap.put(OpcalLogbackConstants.CURRENT_THREAD_ID, OpcalThreadClassicConverter.class.getName());
		PatternLayout patternLayout = new PatternLayout();
		patternLayout.setContext(context);
		patternLayout.setPattern(getPattern());
		patternLayout.setOutputPatternAsHeader(outputPatternAsHeader);
		patternLayout.start();
		this.layout = patternLayout;
		super.start();
	}

}
