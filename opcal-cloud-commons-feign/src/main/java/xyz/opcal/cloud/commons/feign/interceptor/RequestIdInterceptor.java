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

package xyz.opcal.cloud.commons.feign.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import xyz.opcal.cloud.commons.feign.http.RequestIdHandler;
import xyz.opcal.cloud.commons.web.WebConstants;

public class RequestIdInterceptor implements RequestInterceptor {

	private final List<RequestIdHandler> requestIdHandlers = new ArrayList<>();

	public RequestIdInterceptor(List<RequestIdHandler> requestIdHandlers) {
		if (!CollectionUtils.isEmpty(requestIdHandlers)) {
			this.requestIdHandlers.addAll(requestIdHandlers);
		}
	}

	@Override
	public void apply(RequestTemplate template) {
		template.header(WebConstants.HEADER_X_REQUEST_ID, getRequestId());
	}

	private String getRequestId() {
		for (RequestIdHandler requestIdHandler : requestIdHandlers) {
			if (StringUtils.isNotBlank(requestIdHandler.getRequestId())) {
				return requestIdHandler.getRequestId();
			}
		}
		return UUID.randomUUID().toString().replace("-", "");
	}

}
