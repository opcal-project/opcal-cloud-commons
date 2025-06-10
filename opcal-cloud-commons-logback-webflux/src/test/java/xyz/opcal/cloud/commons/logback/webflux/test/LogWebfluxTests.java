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

package xyz.opcal.cloud.commons.logback.webflux.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import xyz.opcal.cloud.commons.web.WebConstants;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class LogWebfluxTests {

	public static final String GET_API = "/get";
	public static final String POST_API = "/post";
	public static final String DELETE_API = "/delete?name={name}&id={id}";

	public static final String IP_1 = "10.0.0.1";
	public static final String IP_2 = "10.0.0.2";
	public static final String IP_3 = "10.0.0.3";
	public static final String IP_4 = "10.0.0.4";

	private @Autowired WebTestClient webTestClient;

	@Test
	@Order(0)
	void testGet() {
		webTestClient.get().uri(GET_API).exchange().expectStatus().isOk();

		webTestClient.get().uri(GET_API) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.exchange().expectStatus().isOk();

		webTestClient.get().uri(GET_API) //
				.header(WebConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString()) //
				.header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_GIF_VALUE) //
				.exchange().expectStatus().isOk();

		webTestClient.get().uri("/skip/get") //
				.header(WebConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString()) //
				.exchange().expectStatus().isOk();
	}

	@Test
	@Order(1)
	void testPost() {

		webTestClient.post().uri(POST_API).bodyValue(UUID.randomUUID()).exchange().expectStatus().isOk();

		webTestClient.post().uri(POST_API).bodyValue(UUID.randomUUID()) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_W_CONNECTING_IP, IP_1) //
				.exchange().expectStatus().isOk();

		webTestClient.post().uri(POST_API).bodyValue(UUID.randomUUID()) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_CF_CONNECTING_IP, IP_2) //
				.exchange().expectStatus().isOk();

		webTestClient.post().uri(POST_API).bodyValue(UUID.randomUUID()) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_X_REAL_IP, IP_3) //
				.exchange().expectStatus().isOk();

		webTestClient.post().uri(POST_API).bodyValue(UUID.randomUUID()) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_X_REAL_IP, WebConstants.LOCALHOST_IP) //
				.exchange().expectStatus().isOk();
	}

	@Test
	@Order(2)
	void testDelete() {

		Map<String, Object> urlVariables = new HashMap<>();
		urlVariables.put("id", System.currentTimeMillis());
		urlVariables.put("name", UUID.randomUUID());

		webTestClient.delete().uri(DELETE_API, urlVariables) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_X_REAL_IP, IP_4) //
				.exchange().expectStatus().isOk();
	}

	@Test
	@Order(3)
	void testFlux() {
		webTestClient.get().uri("/flux") //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_X_REAL_IP, IP_4) //
				.exchange().expectStatus().isOk()
				.expectBodyList(Integer.class).consumeWith(System.out::println);
	}

	@Test
	@Order(4)
	void testEvent() {
		webTestClient.get().uri("/event") //
				.accept(MediaType.TEXT_EVENT_STREAM) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_X_REAL_IP, IP_4) //
				.exchange().expectStatus().isOk()
				.expectBodyList(Integer.class).consumeWith(System.out::println);
	}

	@Test
	@Order(5)
	void testStream() {
		webTestClient.get().uri("/stream") //
				.accept(MediaType.APPLICATION_NDJSON) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_X_REAL_IP, IP_4) //
				.exchange().expectStatus().isOk()
				.expectBodyList(Integer.class).consumeWith(System.out::println);
	}
}
