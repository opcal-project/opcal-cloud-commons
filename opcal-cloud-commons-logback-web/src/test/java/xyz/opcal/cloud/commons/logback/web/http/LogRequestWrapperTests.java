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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;

import jakarta.servlet.ServletInputStream;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
class LogRequestWrapperTests {

	private static final JsonMapper objectMapper = new JsonMapper();

	@Test
	void test() {
		Map<String, Object> content = new HashMap<>();
		content.put("name", "apple");
		content.put("quantity", 10);

		MockHttpServletRequest request = new MockHttpServletRequest(HttpMethod.POST.name(), "/api/product?ca=123");
		request.setServletPath("/api");
		request.setPathInfo("/product");
		request.setQueryString("ca=123");
		request.setParameter("ca", "123");
		request.setContentType(MediaType.APPLICATION_JSON_VALUE);
		request.setContent(objectMapper.writeValueAsBytes(content));

		LogRequestWrapper requestWrapper = new LogRequestWrapper(UUID.randomUUID().toString(), request, objectMapper.writeValueAsString(content));
		try (ServletInputStream inputStream = requestWrapper.getInputStream()) {
			inputStream.setReadListener(null);
			assertTrue(inputStream.isReady());
			assertFalse(inputStream.isFinished());
		} catch (IOException e) {
			log.error("request error", e);
		}
	}
}
