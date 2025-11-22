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

package xyz.opcal.cloud.commons.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = EndpointIntegrationTests.ClientApp.class, properties = {
		"management.endpoints.web.exposure.include=*" }, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class EndpointIntegrationTests {

	private static final String BASE_PATH = new WebEndpointProperties().getBasePath();

	@LocalServerPort
	private int port;

	@Autowired
	WebTestClient webTestClient;

	@Test
	void webAccess() {
		webTestClient.get().uri("http://localhost:" + this.port + BASE_PATH + "/info").exchange().expectStatus().isOk();
	}

	@Configuration
	@EnableAutoConfiguration
	protected static class ClientApp {

	}

}
