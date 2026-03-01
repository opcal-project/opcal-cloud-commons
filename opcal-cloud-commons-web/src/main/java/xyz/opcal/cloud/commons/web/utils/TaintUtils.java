/*
 * Copyright 2020-2026 Opcal.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.opcal.cloud.commons.web.utils;

import org.apache.commons.lang3.RegExUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TaintUtils {

	/**
	 * The regular expression to which this string is to be matched.
	 */
	public static final String B_H_REG_EX = "[\\n\\r\\t]";

	/**
	 * The replacement strings.
	 */
	public static final String B_H_REG_EX_REPLACEMENT = "_";

	public static String cleanTaint(String value) {
		return RegExUtils.replaceAll(value, B_H_REG_EX, B_H_REG_EX_REPLACEMENT);
	}

}
