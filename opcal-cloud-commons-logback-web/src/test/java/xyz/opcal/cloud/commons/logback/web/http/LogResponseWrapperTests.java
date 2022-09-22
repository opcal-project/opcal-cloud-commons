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

package xyz.opcal.cloud.commons.logback.web.http;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.ServletOutputStream;

import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockHttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class LogResponseWrapperTests {

	@Test
	void test() {
		MockHttpServletResponse response = new MockHttpServletResponse();
		LogResponseWrapper responseWrapper = new LogResponseWrapper(UUID.randomUUID().toString(), response);
		try (ServletOutputStream outputStream = responseWrapper.getOutputStream()) {
			outputStream.setWriteListener(null);
			assertTrue(outputStream.isReady());
		} catch (IOException e) {
			log.error("response error: ", e);
		}
	}
}
