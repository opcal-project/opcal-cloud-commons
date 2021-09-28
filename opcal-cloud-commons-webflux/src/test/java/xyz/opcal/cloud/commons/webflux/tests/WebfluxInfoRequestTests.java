/*
 * Copyright 2021-2021 Opcal
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

package xyz.opcal.cloud.commons.webflux.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import xyz.opcal.cloud.commons.webflux.WebConstants;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WebfluxInfoRequestTests {

	public static final String ID_API = "/webflux/request/id";
	public static final String IP_API = "/webflux/request/ip";

	public static final String IP_1 = "10.0.0.1";
	public static final String IP_2 = "10.0.0.2";
	public static final String IP_3 = "10.0.0.3";

	private @Autowired WebTestClient webTestClient;

	@Test
	void infoId() {

		webTestClient.get().uri(ID_API) //
				.accept(MediaType.TEXT_PLAIN).exchange() //
				.expectStatus().isOk() //
				.expectBody(String.class).value(body -> assertTrue(StringUtils.isNotBlank(body)));

		String requestId = String.valueOf(System.currentTimeMillis());

		webTestClient.get().uri(ID_API) //
				.accept(MediaType.TEXT_PLAIN) //
				.header(WebConstants.HEADER_X_REQUEST_ID, requestId) //
				.exchange() //
				.expectStatus().isOk() //
				.expectBody(String.class).value(body -> assertEquals(requestId, body));

	}

	@Test
	void infoIp() {

		webTestClient.get().uri(IP_API) //
				.accept(MediaType.TEXT_PLAIN) //
				.exchange() //
				.expectStatus().isOk() //
				.expectBody(String.class).value(body -> assertEquals(WebConstants.LOCALHOST_IP, body));

		webTestClient.get().uri(IP_API) //
				.accept(MediaType.TEXT_PLAIN) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_W_CONNECTING_IP, IP_1) //
				.exchange() //
				.expectStatus().isOk() //
				.expectBody(String.class).value(body -> assertEquals(IP_1, body));

		webTestClient.get().uri(IP_API) //
				.accept(MediaType.TEXT_PLAIN) //
				.header(WebConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString()) //
				.header(WebConstants.HEADER_CF_CONNECTING_IP, IP_2) //
				.exchange() //
				.expectStatus().isOk() //
				.expectBody(String.class).value(body -> assertEquals(IP_2, body));

		webTestClient.get().uri(IP_API) //
				.accept(MediaType.TEXT_PLAIN) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_X_REAL_IP, IP_3) //
				.exchange() //
				.expectStatus().isOk() //
				.expectBody(String.class).value(body -> assertEquals(IP_3, body));

		webTestClient.get().uri(IP_API) //
				.accept(MediaType.TEXT_PLAIN) //
				.header(WebConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString()) //
				.header(WebConstants.HEADER_X_REAL_IP, WebConstants.LOCALHOST_IP) //
				.exchange() //
				.expectStatus().isOk();
	}

}
