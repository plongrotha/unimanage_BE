package org.plongrotha.unimanage.util;

import org.plongrotha.unimanage.dto.res.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtil {

    private ResponseUtil() {
    }

    // OK Responses (200 OK)
    public static <T> ResponseEntity<ApiResponse<T>> ok() {
        return ok(null, "Success");
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ok(data, "Success");
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        return build(data, message, HttpStatus.OK, true);
    }

    // Generic Success Responses
    public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus status) {
        return success(null, "Success", status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return success(data, "Success", HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return success(data, message, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message, HttpStatus status) {
        return build(data, message, status, true);
    }

    // Error Responses
    public static <T> ResponseEntity<ApiResponse<T>> error(String message) {
        return error(null, message, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
        return error(null, message, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(T data, String message) {
        return error(data, message, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(T data, String message, HttpStatus status) {
        return build(data, message, status, false);
    }

    // Created Responses (201 Created)
    public static <T> ResponseEntity<ApiResponse<T>> created() {
        return created(null, "Created successfully");
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return created(data, "Created successfully");
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        return build(data, message, HttpStatus.CREATED, true);
    }

    /* ===================== COMMON BUILDER ===================== */

    private static <T> ResponseEntity<ApiResponse<T>> build(
            T data,
            String message,
            HttpStatus status,
            boolean success) {

        ApiResponse<T> response = ApiResponse.<T>builder()
                .code(status.value())
                .status(status)
                .success(success)
                .message(message)
                .data(data)
                .build();

        return new ResponseEntity<>(response, status);
    }
}