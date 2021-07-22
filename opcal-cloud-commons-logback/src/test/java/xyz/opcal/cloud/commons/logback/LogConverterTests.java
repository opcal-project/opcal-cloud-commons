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
import org.springframework.test.context.ActiveProfiles;

import xyz.opcal.cloud.commons.logback.configuration.LogTestConfiguration;

@SpringBootTest(classes = LogTestConfiguration.class)
@ActiveProfiles("converter")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class LogConverterTests {

	static Logger logger = LoggerFactory.getLogger(LogConverterTests.class);

	@Test
	@Order(0)
	void testConverter() {
		Assertions.assertTrue(System.currentTimeMillis() > 0);
		logger.info("current logger converter tests");
	}

	@Test
	@Order(1)
	void testCustomThreadId() {
		try {
			MDC.put(OpcalLogbackConstants.MDC_THREAD_ID, String.valueOf(System.currentTimeMillis()));
			Assertions.assertTrue(System.currentTimeMillis() > 0);
			logger.info("current logger converter custom thread id tests");
		} finally {
			MDC.clear();
		}
	}
}
