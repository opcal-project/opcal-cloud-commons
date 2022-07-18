/*
 *  Copyright 2020-2022 Opcal
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

package xyz.opcal.cloud.commons.autoconfigure.web;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import xyz.opcal.cloud.commons.autoconfigure.AutoConfigTestApplication;
import xyz.opcal.cloud.commons.web.servlet.filter.RequestIdFilter;

@ActiveProfiles("web")
@SpringBootTest(classes = AutoConfigTestApplication.class)
class RequestIdConfigTests {

	@Test
	void test(ApplicationContext applicationContext) {
		assertNotNull(applicationContext.getBean(RequestIdFilter.class));
	}
}
