/*
 * Copyright 2020-2025 Opcal
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

package xyz.opcal.cloud.commons.logback.webflux.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import xyz.opcal.cloud.commons.logback.webflux.exception.IOStreamWrapperException;

class BufferUtilsTest {


	@Test
	void outputStreamConsume() {
		var outputStream = new ExceptionOutputStream();
		var consumer = BufferUtils.outputStreamConsume(outputStream);
		assertThrows(IOStreamWrapperException.class, () -> consumer.accept(null));
	}


	static class ExceptionOutputStream extends ByteArrayOutputStream {

		@Override
		public void write(byte[] b) throws IOException {
			throw new IOException("mock write IOException");
		}

	}

}