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

package xyz.opcal.cloud.commons.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.opcal.cloud.commons.web.utils.RequestUtils;

@RestController
public class InfoController {

	@GetMapping("/request/ip")
	public ResponseEntity<String> requestIp(HttpServletRequest request) {
		return new ResponseEntity<>(RequestUtils.getIp(request), HttpStatus.OK);
	}

	@GetMapping("/request/id")
	public ResponseEntity<String> requestId(HttpServletRequest request) {
		return new ResponseEntity<>(RequestUtils.getRequestId(request), HttpStatus.OK);
	}

}
