package xyz.opcal.cloud.commons.logback;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpcalLogbackConstants {

	public static final String MDC_THREAD_ID = "mdcThreadId";
	public static final String CURRENT_THREAD_ID = "currentThreadId";
}
