package com.siemens.useraccountapi.dtos;

import lombok.Data;

@Data
public class GenericResponse<T> {

    private T object;
    private String message;

    public GenericResponse(T object) {
        this.object = object;
    }

    public GenericResponse(String message) {
        this.message = message;
    }
}
