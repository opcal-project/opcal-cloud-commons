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

package xyz.opcal.cloud.commons.logback.http.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogRequestConfigTests {

	@Test
	@Order(1)
	void testDefault() {
		LogRequestConfig config = new LogRequestConfig();
		assertNotNull(config.getDisableMediaTypes());
		assertNull(config.getDisablePaths());
	}

	@Test
	@Order(2)
	void disableMediaType() {
		LogRequestConfig config = new LogRequestConfig();
		assertTrue(config.isDisableMediaType(MediaType.APPLICATION_PDF_VALUE));
		assertFalse(config.isDisableMediaType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	@Order(3)
	void testCustomConfig() {
		LogRequestConfig config = new LogRequestConfig();
		config.setDisablePaths(new String[] { "/a", "/b" });
		config.setDisableMediaTypes(new String[] { MediaType.APPLICATION_XML_VALUE });

		assertFalse(ArrayUtils.contains(config.getDisablePaths(), "/c"));
		assertFalse(config.isDisableMediaType(MediaType.APPLICATION_JSON_VALUE));
	}

}
