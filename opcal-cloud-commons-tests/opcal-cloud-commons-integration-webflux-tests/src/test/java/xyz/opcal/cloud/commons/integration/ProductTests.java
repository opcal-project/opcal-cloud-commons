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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import xyz.opcal.cloud.commons.integration.entity.Product;
import xyz.opcal.cloud.commons.web.WebConstants;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductTests {

	public static final String GET_PRODUCT_API = "/product/{id}";
	public static final String ADD_PRODUCT_API = "/product/add";
	public static final String UPDATE_USER_API = "/product/{id}?name={name}&price={price}";

	private @Autowired WebTestClient webTestClient;

	@Test
	void getProduct() {
		Map<String, Object> urlVariables = new HashMap<>();
		urlVariables.put("id", 100);
		webTestClient.get().uri(GET_PRODUCT_API, urlVariables).exchange().expectStatus().isOk();

		webTestClient.get().uri(GET_PRODUCT_API, urlVariables) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.exchange().expectStatus().isOk();
	}

	@Test
	void addProduct() {
		Product product = new Product();
		product.setId(999);
		product.setName("Book");
		product.setPrice(new BigDecimal("9.56"));
		product.setDescription("book");

		webTestClient.post().uri(ADD_PRODUCT_API).bodyValue(product).exchange().expectStatus().isOk();

		webTestClient.post().uri(ADD_PRODUCT_API).bodyValue(product) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_X_REAL_IP, "192.168.20.88") //
				.exchange().expectStatus().isOk();
	}

	@Test
	void updateProduct() {
		Map<String, Object> urlVariables = new HashMap<>();
		urlVariables.put("id", 100);
		urlVariables.put("name", "iphone");
		urlVariables.put("price", "1999.99");
		webTestClient.post().uri(UPDATE_USER_API, urlVariables).exchange().expectStatus().isOk();

		webTestClient.post().uri(UPDATE_USER_API, urlVariables) //
				.header(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis())) //
				.header(WebConstants.HEADER_CF_CONNECTING_IP, "10.1.20.56") //
				.exchange().expectStatus().isOk();
	}

}
