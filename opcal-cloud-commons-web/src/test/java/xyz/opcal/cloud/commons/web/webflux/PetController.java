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

package xyz.opcal.cloud.commons.web.webflux;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import xyz.opcal.cloud.commons.web.domain.Pet;

@Slf4j
@RestController
public class PetController {

	public static final String[] NAMES = new String[] { "Dog", "Cat", "Rabbit" };

	@GetMapping(value = "/pet/{id}")
	public Mono<Pet> getPet(@PathVariable long id) {
		log.info("get pet by id [{}]", id);
		return Mono.just(new Pet(id, NAMES[new Random().nextInt(NAMES.length)]));
	}
}
