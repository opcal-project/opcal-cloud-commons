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

package xyz.opcal.cloud.commons.integration.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import xyz.opcal.cloud.commons.integration.entity.User;
import xyz.opcal.cloud.commons.integration.entity.UserResult;

@Slf4j
@RestController
public class UserController {

	@GetMapping(value = "/user/{id}")
	public User getUser(@PathVariable long id) {
		User user = new User();
		user.setId(id);
		user.setName("Mike");
		user.setAge(10);
		log.info("get user [{}] by id {}", user, id);
		return user;
	}

	@PostMapping(value = "/user/add")
	public ResponseEntity<UserResult> addUser(@RequestBody User user) {
		UserResult result = new UserResult();
		result.setUser(user);
		result.setMsg("success");
		log.info("add user [{}] success", user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping(value = "/user/{id}")
	public ResponseEntity<UserResult> updateUser(@PathVariable long id, @RequestParam String name, @RequestParam int age) {
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setAge(age);

		UserResult result = new UserResult();
		result.setUser(user);
		result.setMsg("success");
		log.info("update user [{}] success", user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
