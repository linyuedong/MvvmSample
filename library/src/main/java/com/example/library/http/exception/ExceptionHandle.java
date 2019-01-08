package com.example.library.http.exception;

import android.net.ParseException;

import com.example.library.Utils.LogUtlis;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

public class ExceptionHandle {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable handleException(Throwable e) {
        ResponeThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponeThrowable(e, Error.Code.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = Error.Message.NETWORD_ERROR;
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponeThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponeThrowable(e, Error.Code.PARSE_ERROR);
            ex.message = Error.Message.PARSE_ERROR;
            return ex;
        } else if (e instanceof ConnectException) {
            LogUtlis.i("ConnectException");
            ex = new ResponeThrowable(e, Error.Code.NETWORD_ERROR);
            ex.message = Error.Message.NETWORD_ERROR;
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponeThrowable(e, Error.Code.SSL_ERROR);
            ex.message = Error.Message.SSL_ERROR;
            return ex;
        } else {
            ex = new ResponeThrowable(e, Error.Code.UNKNOWN);
            ex.message = Error.Message.UNKNOWN;
            return ex;
        }
    }


    /**
     * ServerException发生后，将自动转换为ResponeThrowable返回
     */
    class ServerException extends RuntimeException {
        int code;
        String message;
    }

}
