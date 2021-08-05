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

package xyz.opcal.cloud.commons.logback.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import lombok.experimental.UtilityClass;
import xyz.opcal.cloud.commons.logback.web.LogWebConstants;

@UtilityClass
public class RequestUtils {

    public static final String B_H_REG_EX = "[\\n\\r\\t]";
    public static final String B_H_REG_EX_REPLACEMENT = "_";

    public static String cleanTaint(String value) {
        return RegExUtils.replaceAll(value, B_H_REG_EX, B_H_REG_EX_REPLACEMENT);
    }

    public static String getRequestId(@NotNull HttpServletRequest request) {
        String requestId = request.getHeader(LogWebConstants.HEADER_X_REQUEST_ID);
        return cleanTaint(requestId);
    }

    public static String getIp(@NotNull HttpServletRequest request) {
        String workerClientIp = request.getHeader(LogWebConstants.HEADER_W_CONNECTING_IP);
        String ip = StringUtils.isNotBlank(workerClientIp) ? workerClientIp : request.getHeader(LogWebConstants.HEADER_CF_CONNECTING_IP);
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader(LogWebConstants.HEADER_X_REAL_IP);
        }
        if (StringUtils.isBlank(ip) || StringUtils.equals(ip, LogWebConstants.LOCALHOST_IP)) {
            ip = request.getRemoteAddr();
        }
        return cleanTaint(ip);
    }
}
