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

package xyz.opcal.cloud.commons.web.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(WebfluxConfiguration.class)
class WebfluxTests {

	@Test
	void springMvcTest(WebApplicationContext wac) throws Exception {
		long id = 22;
		WebTestClient webTestClient = WebTestClient.bindToApplicationContext(wac).build();
		webTestClient.get().uri("/pet/{id}", id)
				.accept(MediaType.APPLICATION_JSON)
				.exchange().expectStatus().isOk();
	}
}