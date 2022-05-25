/*
 * Copyright 2022 Opcal
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

package xyz.opcal.cloud.commons.web.context;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import xyz.opcal.cloud.commons.web.utils.PrivateConstructorUtils;

class RequestThreadContextTests {

	static final String TEST_KEY = "timestamp";

	@Test
	void testConstructor() {
		assertThrows(UnsupportedOperationException.class, () -> PrivateConstructorUtils.invokePrivateConstructor(RequestThreadContext.class));
	}

	@Test
	void test() {
		assertNull(RequestThreadContext.get(TEST_KEY));
		assertDoesNotThrow(() -> RequestThreadContext.setRequestContext(Collections.synchronizedMap(new HashMap<>())));
		assertThrows(IllegalArgumentException.class, () -> RequestThreadContext.put(null, null));
		assertDoesNotThrow(() -> RequestThreadContext.put(TEST_KEY, System.currentTimeMillis()));
		assertNotNull(RequestThreadContext.get(TEST_KEY));
		assertNull(RequestThreadContext.get(null));
		assertDoesNotThrow(() -> RequestThreadContext.remove(TEST_KEY));
		assertDoesNotThrow(() -> RequestThreadContext.remove(null));
		assertDoesNotThrow(RequestThreadContext::clear);
		assertDoesNotThrow(() -> RequestThreadContext.setRequestContext(null));
	}

}