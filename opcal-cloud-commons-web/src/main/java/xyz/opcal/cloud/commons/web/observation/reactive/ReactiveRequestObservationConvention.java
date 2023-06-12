/*
 * Copyright 2020-2023 Opcal
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

package xyz.opcal.cloud.commons.web.observation.reactive;

import org.springframework.http.server.reactive.observation.DefaultServerRequestObservationConvention;
import org.springframework.http.server.reactive.observation.ServerRequestObservationContext;

import io.micrometer.common.KeyValue;
import io.micrometer.common.KeyValues;
import xyz.opcal.cloud.commons.web.WebConstants;
import xyz.opcal.cloud.commons.web.utils.ServerHttpRequestUtils;

public class ReactiveRequestObservationConvention extends DefaultServerRequestObservationConvention {

	@Override
	public KeyValues getLowCardinalityKeyValues(ServerRequestObservationContext context) {
		return super.getLowCardinalityKeyValues(context)
				.and(KeyValue.of(WebConstants.HEADER_X_REQUEST_ID.toLowerCase(), ServerHttpRequestUtils.getRequestId(context.getCarrier())));
	}
}
