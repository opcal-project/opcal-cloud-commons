/*
 * Copyright 2021 Opcal
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

package xyz.opcal.cloud.commons.web.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xyz.opcal.cloud.commons.web.WebConstants;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class InfoRequestTests {

	public static final String ID_API = "/request/id";
	public static final String IP_API = "/request/ip";

	public static final String IP_1 = "10.0.0.1";
	public static final String IP_2 = "10.0.0.2";
	public static final String IP_3 = "10.0.0.3";

	private @Autowired TestRestTemplate testRestTemplate;

	@Test
	void infoId() {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> result1 = testRestTemplate.exchange(ID_API, HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, result1.getStatusCode());
		assertTrue(StringUtils.isNotBlank(result1.getBody()));

		String requestId = String.valueOf(System.currentTimeMillis());
		headers.set(WebConstants.HEADER_X_REQUEST_ID, requestId);
		ResponseEntity<String> result2 = testRestTemplate.exchange(ID_API, HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, result2.getStatusCode());
		assertEquals(requestId, result2.getBody());
	}

	@Test
	void infoIp() {

		ResponseEntity<String> result1 = testRestTemplate.getForEntity(IP_API, String.class);
		assertEquals(HttpStatus.OK, result1.getStatusCode());
		assertEquals(WebConstants.LOCALHOST_IP, result1.getBody());

		HttpHeaders headers = new HttpHeaders();
		headers.set(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
		headers.set(WebConstants.HEADER_W_CONNECTING_IP, IP_1);
		ResponseEntity<String> result2 = testRestTemplate.exchange(IP_API, HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, result2.getStatusCode());
		assertEquals(IP_1, result2.getBody());

		headers = new HttpHeaders();
		headers.set(WebConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString());
		headers.set(WebConstants.HEADER_CF_CONNECTING_IP, IP_2);
		ResponseEntity<String> result3 = testRestTemplate.exchange(IP_API, HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, result3.getStatusCode());
		assertEquals(IP_2, result3.getBody());

		headers = new HttpHeaders();
		headers.set(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
		headers.set(WebConstants.HEADER_X_REAL_IP, IP_3);
		ResponseEntity<String> result4 = testRestTemplate.exchange(IP_API, HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, result4.getStatusCode());
		assertEquals(IP_3, result4.getBody());

		headers = new HttpHeaders();
		headers.set(WebConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString());
		headers.set(WebConstants.HEADER_X_REAL_IP, WebConstants.LOCALHOST_IP);
		ResponseEntity<String> result5 = testRestTemplate.exchange(IP_API, HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, result5.getStatusCode());
	}

}
