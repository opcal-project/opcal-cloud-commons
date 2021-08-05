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

package xyz.opcal.cloud.commons.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xyz.opcal.cloud.commons.integration.entity.User;
import xyz.opcal.cloud.commons.logback.web.LogWebConstants;
import xyz.opcal.cloud.commons.logback.web.annotation.EnableRequestId;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableRequestId
class LogRequestIdTests {

    public static final String GET_USER_API = "/user/{id}";
    private @Autowired TestRestTemplate testRestTemplate;

    @Test
    void getUser() {
        Map<String, Object> urlVariables = new HashMap<>();
        urlVariables.put("id", 100);
        ResponseEntity<User> result1 = testRestTemplate.getForEntity(GET_USER_API, User.class, urlVariables);
        assertThat(result1.getStatusCode()).isEqualTo(HttpStatus.OK);

        HttpHeaders headers = new HttpHeaders();
        headers.set(LogWebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));

        ResponseEntity<User> result2 = testRestTemplate.exchange(GET_USER_API, HttpMethod.GET, new HttpEntity<>(headers), User.class, urlVariables);
        assertThat(result2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
