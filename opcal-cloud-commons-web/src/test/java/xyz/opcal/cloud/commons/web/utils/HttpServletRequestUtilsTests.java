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

package xyz.opcal.cloud.commons.web.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static xyz.opcal.cloud.commons.web.TestConstants.IP_1;
import static xyz.opcal.cloud.commons.web.TestConstants.IP_2;
import static xyz.opcal.cloud.commons.web.TestConstants.IP_3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.mock.web.MockHttpServletRequest;

import xyz.opcal.cloud.commons.web.WebConstants;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HttpServletRequestUtilsTests {

	private MockHttpServletRequest request;

	@BeforeEach
	void setup() {
		this.request = new MockHttpServletRequest();
		this.request.setScheme("http");
		this.request.setServerName("localhost");
		this.request.setServerPort(80);
	}

	@Test
	@Order(1)
	void cleanHeaderTaint() {
		String taintName = "x-taint-header";
		String taintValue = "1\n2\r3\t4\r\n5";
		this.request.addHeader(taintName, taintValue);
		assertEquals("1_2_3_4__5", HttpServletRequestUtils.cleanHeaderTaint(this.request, taintName));
	}

	@Test
	@Order(2)
	void getRequestId() {
		String requestId = String.valueOf(System.currentTimeMillis());
		this.request.addHeader(WebConstants.HEADER_X_REQUEST_ID, requestId);
		assertEquals(requestId, HttpServletRequestUtils.getRequestId(this.request));
	}

	@Test
	@Order(3)
	void getIp1() {
		assertEquals(WebConstants.LOCALHOST_IP, HttpServletRequestUtils.getIp(this.request));
	}

	@Test
	@Order(4)
	void getIp2() {
		this.request.addHeader(WebConstants.HEADER_W_CONNECTING_IP, IP_1);
		assertEquals(IP_1, HttpServletRequestUtils.getIp(this.request));
	}

	@Test
	@Order(5)
	void getIp3() {
		this.request.addHeader(WebConstants.HEADER_CF_CONNECTING_IP, IP_2);
		assertEquals(IP_2, HttpServletRequestUtils.getIp(this.request));
	}

	@Test
	@Order(6)
	void getIp4() {
		this.request.addHeader(WebConstants.HEADER_X_REAL_IP, IP_3);
		assertEquals(IP_3, HttpServletRequestUtils.getIp(this.request));
	}
}
