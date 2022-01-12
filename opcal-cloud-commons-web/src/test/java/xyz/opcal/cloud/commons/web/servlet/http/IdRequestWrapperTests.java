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

package xyz.opcal.cloud.commons.web.servlet.http;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import xyz.opcal.cloud.commons.web.WebConstants;

class IdRequestWrapperTests {

    @Test
    void requestIdTest() {
        String requestId = UUID.randomUUID().toString().replace("-", "");
        MockHttpServletRequest request = new MockHttpServletRequest();
        IdRequestWrapper idRequestWrapper = new IdRequestWrapper(requestId, request);
        assertEquals(requestId, idRequestWrapper.getRequestId());

        assertNotNull(idRequestWrapper.getHeader(WebConstants.HEADER_X_REQUEST_ID));

        assertNotNull(idRequestWrapper.getHeaderNames());
        assertThat(WebConstants.HEADER_X_REQUEST_ID, in(Collections.list(idRequestWrapper.getHeaderNames())));

        assertNotNull(idRequestWrapper.getHeaders(WebConstants.HEADER_X_REQUEST_ID));
        assertThat(requestId, in(Collections.list(idRequestWrapper.getHeaders(WebConstants.HEADER_X_REQUEST_ID))));
    }
}
