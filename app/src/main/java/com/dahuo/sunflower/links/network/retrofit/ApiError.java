package com.dahuo.sunflower.links.network.retrofit;

/**
 * @author YanLu
 * @since 17/3/21
 */

public class ApiError {
    public String errorMessage;
    public String errorCode;

    public ApiError(String message, String code) {
        this.errorMessage = message;
        this.errorCode = code;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "errorMessage='" + errorMessage + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
