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

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;

import lombok.experimental.UtilityClass;
import xyz.opcal.cloud.commons.logback.webflux.exception.IOStreamWrapperException;

@UtilityClass
public class BufferUtils {

	public static Publisher<DataBuffer> bufferingWrap(Publisher<? extends DataBuffer> body, Consumer<byte[]> consumer) {
		return DataBufferUtils.join(body).defaultIfEmpty(DefaultDataBufferFactory.sharedInstance.wrap(new byte[0])).map(dataBuffer -> {
			byte[] bytes = new byte[dataBuffer.readableByteCount()];
			dataBuffer.read(bytes);
			DataBufferUtils.release(dataBuffer);
			DefaultDataBuffer wrappedDataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(bytes);
			consumer.accept(bytes);
			return wrappedDataBuffer;
		});
	}


	public static Consumer<byte[]> outputStreamConsume(OutputStream outputStream) {
		return bytes -> {
			try {
				outputStream.write(bytes);
			} catch (IOException e) {
				throw new IOStreamWrapperException(e);
			}
		};
	}
}
