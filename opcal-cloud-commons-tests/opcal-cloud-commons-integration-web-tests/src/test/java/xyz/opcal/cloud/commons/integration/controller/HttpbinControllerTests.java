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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import xyz.opcal.cloud.commons.logback.web.annotation.EnableLogRequest;
import xyz.opcal.cloud.commons.logback.web.annotation.EnableLogRequestId;
import xyz.opcal.cloud.commons.feign.annotation.FeignRequestId;
import xyz.opcal.cloud.commons.web.WebConstants;

@EnableLogRequest
@EnableLogRequestId
@FeignRequestId
@EnableFeignClients
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpbinControllerTests {

	public static final String HTTPBIN_API = "/httpbin/get";

	private @Autowired TestRestTemplate testRestTemplate;

	@Test
	void httpbin() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(WebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
		headers.set(WebConstants.HEADER_X_REAL_IP, "10.0.0.4");
		ResponseEntity<String> result = testRestTemplate.exchange(HTTPBIN_API, HttpMethod.GET, new HttpEntity<>(headers), String.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}