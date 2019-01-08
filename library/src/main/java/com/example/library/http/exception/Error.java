package com.example.library.http.exception;

public class Error {

    public static class Code{
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

    }

    public static class Message{

        /**
         * 未知错误
         */
        public static final String UNKNOWN = "未知错误";
        /**
         * 解析错误
         */
        public static final String PARSE_ERROR = "解析错误";
        /**
         * 网络错误
         */
        public static final String NETWORD_ERROR = "网络错误";
        /**
         * 协议出错
         */
        public static final String HTTP_ERROR = "协议出错";

        /**
         * 证书出错
         */
        public static final String SSL_ERROR = "证书出错";

    }
}
