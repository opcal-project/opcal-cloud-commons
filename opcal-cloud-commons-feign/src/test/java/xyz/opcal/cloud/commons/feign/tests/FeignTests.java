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

package xyz.opcal.cloud.commons.feign.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import xyz.opcal.cloud.commons.feign.FeignTestApplication;
import xyz.opcal.cloud.commons.web.WebConstants;

@SpringBootTest(classes = FeignTestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class FeignTests {

	private @Autowired WebTestClient webTestClient;

	@Test
	void test() {
		webTestClient.get().uri("/headers") //
				.exchange().expectStatus().isOk();

		webTestClient.get().uri("/headers") //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.exchange().expectStatus().isOk();

	}

}
