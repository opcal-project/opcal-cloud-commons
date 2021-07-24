package xyz.opcal.cloud.commons.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xyz.opcal.cloud.commons.integration.entity.User;
import xyz.opcal.cloud.commons.integration.entity.UserResult;
import xyz.opcal.cloud.commons.logback.web.LogWebConstants;
import xyz.opcal.cloud.commons.logback.web.annotation.EnableLogRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableLogRequest
@TestMethodOrder(OrderAnnotation.class)
class LogRequestTests {

    public static final String ADD_USER_API = "/user/add";
    public static final String UPDATE_USER_API = "/user/{id}?name={name}&age={age}";
    private @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @Order(0)
    void addUser() {
        User user = new User();
        user.setId(1000);
        user.setName("Tom");
        user.setAge(15);

        ResponseEntity<UserResult> result1 = testRestTemplate.postForEntity(ADD_USER_API, user, UserResult.class);
        assertEquals(HttpStatus.OK, result1.getStatusCode());

        HttpHeaders headers = new HttpHeaders();
        headers.set(LogWebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
        headers.set(LogWebConstants.HEADER_W_CONNECTING_IP, "10.0.0.1");
        ResponseEntity<UserResult> result2 = testRestTemplate.postForEntity(ADD_USER_API, new HttpEntity<>(user, headers), UserResult.class);
        assertEquals(HttpStatus.OK, result2.getStatusCode());

        headers = new HttpHeaders();
        headers.set(LogWebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
        headers.set(LogWebConstants.HEADER_CF_CONNECTING_IP, "10.0.0.2");
        ResponseEntity<UserResult> result3 = testRestTemplate.postForEntity(ADD_USER_API, new HttpEntity<>(user, headers), UserResult.class);
        assertEquals(HttpStatus.OK, result3.getStatusCode());

        headers = new HttpHeaders();
        headers.set(LogWebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
        headers.set(LogWebConstants.HEADER_X_REAL_IP, "10.0.0.3");
        ResponseEntity<UserResult> result4 = testRestTemplate.postForEntity(ADD_USER_API, new HttpEntity<>(user, headers), UserResult.class);
        assertEquals(HttpStatus.OK, result4.getStatusCode());

        headers = new HttpHeaders();
        headers.set(LogWebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
        headers.set(LogWebConstants.HEADER_X_REAL_IP, LogWebConstants.LOCALHOST_IP);
        ResponseEntity<UserResult> result5 = testRestTemplate.postForEntity(ADD_USER_API, new HttpEntity<>(user, headers), UserResult.class);
        assertEquals(HttpStatus.OK, result5.getStatusCode());
    }

    @Test
    @Order(1)
    void updateUser() {

        Map<String, Object> urlVariables = new HashMap<>();
        urlVariables.put("id", 999);
        urlVariables.put("name", "Marry");
        urlVariables.put("age", 20);

        HttpHeaders headers = new HttpHeaders();
        headers.set(LogWebConstants.HEADER_X_REQUEST_ID, String.valueOf(System.currentTimeMillis()));
        headers.set(LogWebConstants.HEADER_X_REAL_IP, "10.0.0.4");
        ResponseEntity<UserResult> result = testRestTemplate.postForEntity(UPDATE_USER_API, new HttpEntity<>(headers), UserResult.class, urlVariables);
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

}
