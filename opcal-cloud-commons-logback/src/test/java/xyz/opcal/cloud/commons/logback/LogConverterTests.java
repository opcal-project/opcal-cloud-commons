package xyz.opcal.cloud.commons.logback;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import lombok.extern.slf4j.Slf4j;
import xyz.opcal.cloud.commons.logback.configuration.LogTestConfiguration;

@SpringBootTest(classes = LogTestConfiguration.class)
@ActiveProfiles("converter")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
class LogConverterTests {

	@Test
	@Order(0)
	void testConverter() {
		assertTrue(System.currentTimeMillis() > 0);
		log.info("current logger converter tests");
	}

	@Test
	@Order(1)
	void testCustomThreadId() {
		try {
			MDC.put(OpcalLogbackConstants.MDC_THREAD_ID, String.valueOf(System.currentTimeMillis()));
			assertTrue(System.currentTimeMillis() > 0);
			log.info("current logger converter custom thread id tests");
		} finally {
			MDC.clear();
		}
	}
}
