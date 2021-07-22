package xyz.opcal.cloud.commons.logback;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import xyz.opcal.cloud.commons.logback.configuration.LogTestConfiguration;

@SpringBootTest(classes = LogTestConfiguration.class)
@ActiveProfiles("encoder")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class LogEncoderTests {

	static Logger logger = LoggerFactory.getLogger(LogEncoderTests.class);

	@Test
	@Order(0)
	void testEncoder() {
		Assertions.assertTrue(System.currentTimeMillis() > 0);
		logger.info("current logger encoder tests");
	}

	@Test
	@Order(1)
	void testCustomThreadId() {
		try {
			MDC.put(OpcalLogbackConstants.MDC_THREAD_ID, String.valueOf(System.currentTimeMillis()));
			Assertions.assertTrue(System.currentTimeMillis() > 0);
			logger.info("current logger encoder custom thread id tests");
		} finally {
			MDC.clear();
		}
	}
}
