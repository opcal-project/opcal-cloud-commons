/*
 * Copyright 2021-2022 Opcal
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

package xyz.opcal.cloud.commons.logback.http.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RefreshScope
public class LogRequestConfig {

	private String[] disableMediaTypes = new String[] { MediaType.APPLICATION_PDF_VALUE.toLowerCase(), MediaType.APPLICATION_XHTML_XML_VALUE.toLowerCase(),
			MediaType.IMAGE_GIF_VALUE.toLowerCase(), MediaType.IMAGE_JPEG_VALUE.toLowerCase(), MediaType.IMAGE_PNG_VALUE.toLowerCase(),
			MediaType.TEXT_HTML_VALUE.toLowerCase(), MediaType.TEXT_MARKDOWN_VALUE.toLowerCase() };
	private String[] disablePaths;

	public boolean isDisableMediaType(String mediaType) {
		return ArrayUtils.contains(getDisableMediaTypes(), StringUtils.lowerCase(mediaType));
	}

}
