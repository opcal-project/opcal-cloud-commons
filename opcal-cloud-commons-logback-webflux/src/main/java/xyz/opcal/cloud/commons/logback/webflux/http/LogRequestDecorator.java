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

package xyz.opcal.cloud.commons.logback.webflux.http;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.FastByteArrayOutputStream;

import reactor.core.publisher.Flux;
import xyz.opcal.cloud.commons.logback.webflux.utils.BufferUtils;

public class LogRequestDecorator extends ServerHttpRequestDecorator {

	private final FastByteArrayOutputStream bodyStream;

	public LogRequestDecorator(ServerHttpRequest request) {
		super(request);
		bodyStream = new FastByteArrayOutputStream();
	}

	@Override
	public Flux<DataBuffer> getBody() {
		return Flux.from(BufferUtils.bufferingWrap(super.getBody(), BufferUtils.outputStreamConsume(bodyStream)));
	}

	@Override
	public String toString() {
		return bodyStream.toString();
	}

	public void close() {
		bodyStream.close();
	}
}
