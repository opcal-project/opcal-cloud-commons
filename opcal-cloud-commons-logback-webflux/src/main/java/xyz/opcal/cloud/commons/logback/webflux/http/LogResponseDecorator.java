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

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.util.FastByteArrayOutputStream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.logback.webflux.utils.BufferUtils;

public class LogResponseDecorator extends ServerHttpResponseDecorator {

	private final FastByteArrayOutputStream bodyStream;

	public LogResponseDecorator(ServerHttpResponse response) {
		super(response);
		bodyStream = new FastByteArrayOutputStream();
	}

	@Override
	public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

		return super.writeWith(BufferUtils.bufferingWrap(body, BufferUtils.outputStreamConsume(bodyStream)));
	}

	@Override
	public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
		return super.writeAndFlushWith(
				Flux.from(body).map(publisher -> BufferUtils.bufferingWrap(publisher, BufferUtils.outputStreamConsume(bodyStream))));
	}

	@Override
	public String toString() {
		return bodyStream.toString();
	}

	public void close() {
		bodyStream.close();
	}
}
