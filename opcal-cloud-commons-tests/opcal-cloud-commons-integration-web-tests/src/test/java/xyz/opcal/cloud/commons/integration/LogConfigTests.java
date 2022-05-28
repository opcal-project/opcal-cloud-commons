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

package xyz.opcal.cloud.commons.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import xyz.opcal.cloud.commons.logback.http.config.LogRequestConfig;

@ActiveProfiles("logconfig")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class LogConfigTests {

	static final String DISABLE_PATH = "/disable-test-path";
	static final String DISABLE_MEDIA_TYPES = "application/pdf";

	@Autowired
	LogRequestConfig logRequestConfig;

	@Test
	@Order(0)
	void disablePath() {
		assertTrue(ArrayUtils.isNotEmpty(logRequestConfig.getDisablePaths()));
		assertEquals(DISABLE_PATH, logRequestConfig.getDisablePaths()[0]);
	}

	@Test
	@Order(1)
	void disableMediaTypes() {
		assertTrue(ArrayUtils.isNotEmpty(logRequestConfig.getDisableMediaTypes()));
		assertEquals(DISABLE_MEDIA_TYPES, logRequestConfig.getDisableMediaTypes()[0]);
	}

}
