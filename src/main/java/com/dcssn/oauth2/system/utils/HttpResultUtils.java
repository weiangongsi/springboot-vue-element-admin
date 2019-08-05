package com.dcssn.oauth2.system.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;

/**
 * 返回结果
 *
 * @author lihaoyang
 * @since 2019/7/22
 */
public class HttpResultUtils {

    public static HttpResult success() {
        return new HttpResult(true);
    }

    public static HttpResult success(String message) {
        return new HttpResult(true, message);
    }

    public static HttpResult success(Object data) {
        return new HttpResult(true, "", data);
    }

    public static HttpResult success(String message, Object data) {
        return new HttpResult(true, message, data);
    }

    public static HttpResult fail() {
        return new HttpResult(false);
    }

    public static HttpResult fail(String message) {
        return new HttpResult(false, message);
    }

    public static HttpResult fail(Object data) {
        return new HttpResult(false, "", data);
    }

    public static HttpResult fail(String message, Object data) {
        return new HttpResult(false, message, data);
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class HttpResult extends HashMap<String, Object> {

        /**
         * 是否成功
         */
        private static final String SUCCESS_KEY = "success";

        /**
         * 消息
         */
        private static final String MESSAGE_KEY = "message";

        /**
         * 数据
         */
        private static final String DATA_KEY = "data";

        public HttpResult(boolean success) {
            put(SUCCESS_KEY, success);
        }

        public HttpResult(boolean success, String message) {
            put(SUCCESS_KEY, success);
            put(MESSAGE_KEY, message);
        }

        public HttpResult(boolean success, Object data) {
            put(SUCCESS_KEY, success);
            put(DATA_KEY, data);
        }

        public HttpResult(boolean success, String message, Object data) {
            put(SUCCESS_KEY, success);
            put(MESSAGE_KEY, message);
            put(DATA_KEY, data);
        }

    }
}
