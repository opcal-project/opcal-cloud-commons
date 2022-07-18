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
