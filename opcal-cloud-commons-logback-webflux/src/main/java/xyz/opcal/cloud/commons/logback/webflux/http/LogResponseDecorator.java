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

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.Channels;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.util.FastByteArrayOutputStream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class LogResponseDecorator extends ServerHttpResponseDecorator implements Closeable {

	private final FastByteArrayOutputStream bodyStream;

	public LogResponseDecorator(ServerHttpResponse response) {
		super(response);
		bodyStream = new FastByteArrayOutputStream();
	}

	@Override
	public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
		return super.writeWith(DataBufferUtils.join(body).doOnNext(dataBuffer -> transferBuffer(dataBuffer, bodyStream)));
	}

	@Override
	public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
		return super.writeAndFlushWith(
				Flux.from(body).map(publisher -> DataBufferUtils.join(publisher).map(dataBuffer -> transferBuffer(dataBuffer, bodyStream))));
	}

	private DataBuffer transferBuffer(DataBuffer dataBuffer, FastByteArrayOutputStream outputStream) {
		try {
			Channels.newChannel(outputStream).write(dataBuffer.toByteBuffer().asReadOnlyBuffer());
		} catch (IOException e) {
			// do nothing
		}
		return dataBuffer;
	}

	@Override
	public String toString() {
		return bodyStream.toString();
	}

	@Override
	public void close() {
		bodyStream.close();
	}
}
