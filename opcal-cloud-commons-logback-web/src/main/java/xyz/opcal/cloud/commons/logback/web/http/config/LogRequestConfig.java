package xyz.opcal.cloud.commons.logback.web.http.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(LogRequestConfig.PREFIX)
@RefreshScope
public class LogRequestConfig {

	public static final String PREFIX = "opcal.cloud.log.web";

	private String[] disableMediaTypes = new String[] { MediaType.APPLICATION_PDF_VALUE.toLowerCase(), MediaType.APPLICATION_XHTML_XML_VALUE.toLowerCase(),
			MediaType.IMAGE_GIF_VALUE.toLowerCase(), MediaType.IMAGE_JPEG_VALUE.toLowerCase(), MediaType.IMAGE_PNG_VALUE.toLowerCase(),
			MediaType.TEXT_HTML_VALUE.toLowerCase(), MediaType.TEXT_MARKDOWN_VALUE.toLowerCase() };
	private String[] disablePaths;

	public boolean isDisableMediaType(String mediaType) {
		return ArrayUtils.contains(getDisableMediaTypes(), StringUtils.lowerCase(mediaType));
	}

}
