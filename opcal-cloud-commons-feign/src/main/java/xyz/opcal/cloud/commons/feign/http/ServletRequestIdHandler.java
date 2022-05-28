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

package xyz.opcal.cloud.commons.feign.http;

import java.util.Objects;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import xyz.opcal.cloud.commons.web.WebConstants;

public class ServletRequestIdHandler implements RequestIdHandler {

	@Override
	public String getRequestId() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (Objects.nonNull(requestAttributes)) {
			return (String) requestAttributes.getAttribute(WebConstants.HEADER_X_REQUEST_ID, RequestAttributes.SCOPE_REQUEST);
		}
		return null;
	}
}