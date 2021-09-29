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

package xyz.opcal.cloud.commons.core.web.utils;

import org.apache.commons.lang3.RegExUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TaintUtils {

	public static final String B_H_REG_EX = "[\\n\\r\\t]";
	public static final String B_H_REG_EX_REPLACEMENT = "_";

	public static String cleanTaint(String value) {
		return RegExUtils.replaceAll(value, B_H_REG_EX, B_H_REG_EX_REPLACEMENT);
	}

}
