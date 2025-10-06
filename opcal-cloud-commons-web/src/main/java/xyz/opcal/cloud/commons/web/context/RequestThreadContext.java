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

package xyz.opcal.cloud.commons.web.context;

import java.util.Map;
import java.util.Objects;

import org.jspecify.annotations.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestThreadContext {

	private static final ThreadLocal<Map<String, Object>> requestContext = new ThreadLocal<>();

	public static void setRequestContext(@Nullable Map<String, Object> context) {
		if (Objects.isNull(context)) {
			clear();
		} else {
			requestContext.set(context);
		}
	}

	public static void put(String key, Object val) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("key cannot be null");
		}
		requestContext.get().put(key, val);
	}

	public static void remove(String key) {
		if (key == null) {
			return;
		}
		requestContext.get().remove(key);
	}

	public static void clear() {
		requestContext.remove();
	}

	public static Object get(String key) {
		final Map<String, Object> map = requestContext.get();
		if ((map != null) && (key != null)) {
			return map.get(key);
		} else {
			return null;
		}
	}

}
