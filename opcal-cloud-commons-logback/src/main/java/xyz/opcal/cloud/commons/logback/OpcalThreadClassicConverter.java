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

package xyz.opcal.cloud.commons.logback;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * Custom thread id converter.
 * 
 * @since 0.1.0
 */
public class OpcalThreadClassicConverter extends ClassicConverter {

	private static final long UP_TIMESTAMP = System.currentTimeMillis();

	@Override
	public String convert(ILoggingEvent event) {
		String mdcThreadId = event.getMDCPropertyMap().get(OpcalLogbackConstants.MDC_THREAD_ID);
		if (StringUtils.isBlank(mdcThreadId)) {
			mdcThreadId = Strings.CS.replace(
					UUID.nameUUIDFromBytes(String.valueOf(UP_TIMESTAMP + Thread.currentThread().getId()).getBytes()).toString(),
					"-",
					"");
		}
		return mdcThreadId;

	}

}
