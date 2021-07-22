package xyz.opcal.cloud.commons.logback;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class OpcalThreadClassicConverter extends ClassicConverter {

	@Override
	public String convert(ILoggingEvent event) {
		String systemId = event.getMDCPropertyMap().get(OpcalLogbackConstants.MDC_THREAD_ID);
		if (StringUtils.isBlank(systemId)) {
			systemId = UUID.nameUUIDFromBytes(String.valueOf(Thread.currentThread().getId()).getBytes()).toString().replaceAll("-", "");
		}
		return systemId;

	}

}
