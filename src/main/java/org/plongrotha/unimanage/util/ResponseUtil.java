package org.plongrotha.unimanage.util;

import org.plongrotha.unimanage.dto.res.ApiResponse;
import org.springframework.http.HttpStatus;

public final class ResponseUtil {

    private ResponseUtil() {

    }

    // OK Responses
    public static <T> ApiResponse<T> ok() {
        return ok(null, "Success");
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ok(data, "Success");
    }

    public static <T> ApiResponse<T> ok(T data, String message) {
        return ApiResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(HttpStatus status) {
        return success(null, "Success", status);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Success", HttpStatus.OK);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return success(data, message, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> success(T data, String message, HttpStatus status) {
        return ApiResponse.<T>builder()
                .code(status.value())
                .status(status)
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    // Error Responses
    public static <T> ApiResponse<T> error(String message) {
        return error(null, message, HttpStatus.BAD_REQUEST);
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return error(null, message, status);
    }

    public static <T> ApiResponse<T> error(T data, String message) {
        return error(data, message, HttpStatus.BAD_REQUEST);
    }

    public static <T> ApiResponse<T> error(T data, String message, HttpStatus status) {
        return ApiResponse.<T>builder()
                .code(status.value())
                .status(status)
                .success(false)
                .message(message)
                .data(data)
                .build();
    }

    // Created Responses
    public static <T> ApiResponse<T> created() {
        return created(null, "Created successfully");
    }

    public static <T> ApiResponse<T> created(T data) {
        return created(data, "Created successfully");
    }

    public static <T> ApiResponse<T> created(T data, String message) {
        return build(data, message, HttpStatus.CREATED, true);
    }

    /* ===================== COMMON BUILDER ===================== */

    private static <T> ApiResponse<T> build(
            T data,
            String message,
            HttpStatus status,
            boolean success) {
        return ApiResponse.<T>builder()
                .code(status.value())
                .status(status)
                .success(success)
                .message(message)
                .data(data)
                .build();
    }
}