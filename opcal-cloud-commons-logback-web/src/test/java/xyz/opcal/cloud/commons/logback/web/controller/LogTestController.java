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

package xyz.opcal.cloud.commons.logback.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LogTestController {

    @GetMapping(value = {"/get", "/skip/get"})
    public ResponseEntity<Map<String, Object>> get() {
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "success");
        result.put("time", System.currentTimeMillis());
        log.info("get request");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Map<String, Object>> post(@RequestBody String body) {
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "success");
        result.put("id", UUID.randomUUID());
        log.info("post request body {}", body);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> delete(@RequestParam String name, @RequestParam long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "success");
        result.put("time", System.currentTimeMillis());
        result.put("id", UUID.randomUUID());
        log.info("delete request name {} id {}", name, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
