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

package xyz.opcal.cloud.commons.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import xyz.opcal.cloud.commons.web.WebConstants;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReHttpbinControllerTests {

	public static final String HTTPBIN_API = "/httpbin/ip";
	public static final String FEIGN_API = "/httpbin/feign/ip";
	public static final String REACTIVE_FEIGN_API = "/httpbin/reactivefeign/ip";

	private @Autowired WebTestClient webTestClient;

	@Test
	void testHttpbin() {
		webTestClient.get().uri(HTTPBIN_API) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_X_REAL_IP, "10.0.0.99") //
				.exchange().expectStatus().isOk();
	}

	@Test
	void testFeignIp() {
		webTestClient.get().uri(FEIGN_API) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.exchange().expectStatus().isOk();
	}

	@Test
	void reactiveFeignIp() {
		webTestClient.get().uri(REACTIVE_FEIGN_API) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.exchange().expectStatus().isOk();
	}
}