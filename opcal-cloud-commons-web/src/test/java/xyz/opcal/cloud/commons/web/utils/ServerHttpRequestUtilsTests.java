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

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;

import xyz.opcal.cloud.commons.web.WebConstants;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerHttpRequestUtilsTests {

    @Test
    @Order(1)
    void cleanHeaderTaint() {
        String taintName = "x-taint-header";
        String taintValue = "1\n2\r3\t4\r\n5";
        MockServerHttpRequest request = MockServerHttpRequest.get("/").header(taintName, taintValue).build();
        assertEquals("1_2_3_4__5", ServerHttpRequestUtils.cleanHeaderTaint(request, taintName));
    }

    @Test
    @Order(2)
    void getRequestId() {
        String requestId = String.valueOf(System.currentTimeMillis());
        MockServerHttpRequest request = MockServerHttpRequest.get("/").header(WebConstants.HEADER_X_REQUEST_ID, requestId).build();
        assertEquals(requestId, ServerHttpRequestUtils.getRequestId(request));
    }

    @Test
    @Order(3)
    void getIp1() {
        MockServerHttpRequest request = MockServerHttpRequest.get("/").build();
        assertEquals(WebConstants.LOCALHOST_IP, ServerHttpRequestUtils.getIp(request));
    }

    @Test
    @Order(4)
    void getIp2() {
        MockServerHttpRequest request = MockServerHttpRequest.get("/").header(WebConstants.HEADER_W_CONNECTING_IP, IP_1).build();
        assertEquals(IP_1, ServerHttpRequestUtils.getIp(request));
    }

    @Test
    @Order(5)
    void getIp3() {
        MockServerHttpRequest request = MockServerHttpRequest.get("/").header(WebConstants.HEADER_CF_CONNECTING_IP, IP_2).build();
        assertEquals(IP_2, ServerHttpRequestUtils.getIp(request));
    }

    @Test
    @Order(6)
    void getIp4() {
        MockServerHttpRequest request = MockServerHttpRequest.get("/").header(WebConstants.HEADER_X_REAL_IP, IP_3).build();
        assertEquals(IP_3, ServerHttpRequestUtils.getIp(request));
    }

    @Test
    @Order(7)
    void getIp5() {
        MockServerHttpRequest request = MockServerHttpRequest.get("/").header(WebConstants.HEADER_X_REAL_IP, WebConstants.LOCALHOST_IP).build();
        assertEquals(WebConstants.LOCALHOST_IP, ServerHttpRequestUtils.getIp(request));
    }

}
