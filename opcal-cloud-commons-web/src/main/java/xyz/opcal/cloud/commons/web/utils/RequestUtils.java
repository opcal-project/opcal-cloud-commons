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

package xyz.opcal.cloud.commons.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;
import xyz.opcal.cloud.commons.core.web.WebConstants;
import xyz.opcal.cloud.commons.core.web.utils.TaintUtils;

@UtilityClass
public class RequestUtils {

	public static String cleanHeaderTaint(@NotNull HttpServletRequest request, String headerName) {
		return TaintUtils.cleanTaint(request.getHeader(headerName));
	}

	public static String getRequestId(@NotNull HttpServletRequest request) {
		return cleanHeaderTaint(request, WebConstants.HEADER_X_REQUEST_ID);
	}

	public static String getIp(@NotNull HttpServletRequest request) {
		String workerClientIp = cleanHeaderTaint(request, WebConstants.HEADER_W_CONNECTING_IP);
		String ip = StringUtils.isNotBlank(workerClientIp) ? workerClientIp : cleanHeaderTaint(request, WebConstants.HEADER_CF_CONNECTING_IP);
		if (StringUtils.isBlank(ip)) {
			ip = cleanHeaderTaint(request, WebConstants.HEADER_X_REAL_IP);
		}
		if (StringUtils.isBlank(ip) || StringUtils.equals(ip, WebConstants.LOCALHOST_IP)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}