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

package xyz.opcal.cloud.commons.logback.web.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import xyz.opcal.cloud.commons.logback.web.LogWebApplication;
import xyz.opcal.cloud.commons.logback.web.LogWebConstants;

@SpringBootTest(classes = LogWebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class LogWebTests {

	public static final String GET_API = "/get";
	public static final String POST_API = "/post";
	public static final String DELETE_API = "/delete?name={name}&id={id}";

	private @Autowired TestRestTemplate testRestTemplate;

	@Test
	@Order(0)
	void testGet() {
		ResponseEntity<String> result1 = testRestTemplate.getForEntity(GET_API, String.class);
		assertEquals(HttpStatus.OK, result1.getStatusCode());

		HttpHeaders headers = new HttpHeaders();
		headers.set(LogWebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
		ResponseEntity<String> result2 = testRestTemplate.exchange(GET_API, HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, result2.getStatusCode());

		headers = new HttpHeaders();
		headers.set(LogWebConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString());
		headers.setContentType(MediaType.IMAGE_GIF);
		ResponseEntity<String> result3 = testRestTemplate.exchange(GET_API, HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, result3.getStatusCode());

		headers = new HttpHeaders();
		headers.set(LogWebConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString());
		ResponseEntity<String> result4 = testRestTemplate.exchange("/skip/get", HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, result4.getStatusCode());
	}

	@Test
	@Order(1)
	void testPost() {

		ResponseEntity<String> result1 = testRestTemplate.postForEntity(POST_API, UUID.randomUUID(), String.class);
		assertEquals(HttpStatus.OK, result1.getStatusCode());

		HttpHeaders headers = new HttpHeaders();
		headers.set(LogWebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
		headers.set(LogWebConstants.HEADER_W_CONNECTING_IP, "10.0.0.1");
		ResponseEntity<String> result2 = testRestTemplate.postForEntity(POST_API, new HttpEntity<>(UUID.randomUUID(), headers), String.class);
		assertEquals(HttpStatus.OK, result2.getStatusCode());

		headers = new HttpHeaders();
		headers.set(LogWebConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString());
		headers.set(LogWebConstants.HEADER_CF_CONNECTING_IP, "10.0.0.2");
		ResponseEntity<String> result3 = testRestTemplate.postForEntity(POST_API, new HttpEntity<>(UUID.randomUUID(), headers), String.class);
		assertEquals(HttpStatus.OK, result3.getStatusCode());

		headers = new HttpHeaders();
		headers.set(LogWebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
		headers.set(LogWebConstants.HEADER_X_REAL_IP, "10.0.0.3");
		ResponseEntity<String> result4 = testRestTemplate.postForEntity(POST_API, new HttpEntity<>(UUID.randomUUID(), headers), String.class);
		assertEquals(HttpStatus.OK, result4.getStatusCode());

		headers = new HttpHeaders();
		headers.set(LogWebConstants.HEADER_X_REQUEST_ID, UUID.randomUUID().toString());
		headers.set(LogWebConstants.HEADER_X_REAL_IP, LogWebConstants.LOCALHOST_IP);
		ResponseEntity<String> result5 = testRestTemplate.postForEntity(POST_API, new HttpEntity<>(UUID.randomUUID(), headers), String.class);
		assertEquals(HttpStatus.OK, result5.getStatusCode());
	}

	@Test
	@Order(2)
	void testDelete() {

		Map<String, Object> urlVariables = new HashMap<>();
		urlVariables.put("id", System.currentTimeMillis());
		urlVariables.put("name", UUID.randomUUID());

		HttpHeaders headers = new HttpHeaders();
		headers.set(LogWebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
		headers.set(LogWebConstants.HEADER_X_REAL_IP, "10.0.0.4");
		ResponseEntity<String> result = testRestTemplate.exchange(DELETE_API, HttpMethod.DELETE, new HttpEntity<>(headers), String.class, urlVariables);
		assertEquals(HttpStatus.OK, result.getStatusCode());

	}

}
