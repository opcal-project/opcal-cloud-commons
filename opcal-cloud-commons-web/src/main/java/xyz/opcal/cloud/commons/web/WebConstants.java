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

package xyz.opcal.cloud.commons.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebConstants {

	public static final String HEADER_X_REQUEST_ID = "X-Request-Id";
	public static final String HEADER_X_REAL_IP = "x-real-ip";

	public static final String HEADER_W_CONNECTING_IP = "worker-connecting-ip";
	public static final String HEADER_CF_CONNECTING_IP = "CF-Connecting-IP";

	public static final String LOCALHOST_IP = "127.0.0.1";
}
