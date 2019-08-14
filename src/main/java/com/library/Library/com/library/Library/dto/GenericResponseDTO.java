package com.library.Library.com.library.Library.dto;

import java.io.Serializable;

public class GenericResponseDTO<T> implements Serializable {
    private String message;
    private String statusCode;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GenericResponseDTO{" +
                "message='" + message + '\'' +
                '}';
    }

    public static GenericResponseDTO generateErrorResponseDTO(){
        GenericResponseDTO dto = new GenericResponseDTO();
        dto.setMessage("Some error occurred");
        dto.setStatusCode("500");
        return dto;
    }

    public static GenericResponseDTO generateSuccessResponseDTO(String msg){
        GenericResponseDTO dto = new GenericResponseDTO();
        dto.setStatusCode("200");
        dto.setMessage(msg);
        return dto;
    }

    public static GenericResponseDTO generateErrorResponseDTO(String msg){
        GenericResponseDTO dto = new GenericResponseDTO();
        dto.setMessage(msg);
        dto.setStatusCode("500");
        return dto;
    }

    public GenericResponseDTO(String message, String statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public GenericResponseDTO() {
    }
}
