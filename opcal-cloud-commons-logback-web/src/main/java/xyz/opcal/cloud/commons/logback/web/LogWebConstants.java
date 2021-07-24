package xyz.opcal.cloud.commons.logback.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogWebConstants {

    public static final String HEADER_X_REQUEST_ID = "X-Request-Id";
    public static final String HEADER_X_REAL_IP = "x-real-ip";

    public static final String HEADER_W_CONNECTING_IP = "worker-connecting-ip";
    public static final String HEADER_CF_CONNECTING_IP = "CF-Connecting-IP";

    public static final String LOCALHOST_IP = "127.0.0.1";
}
