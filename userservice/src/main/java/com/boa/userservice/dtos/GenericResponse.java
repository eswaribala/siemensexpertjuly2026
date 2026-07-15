package com.boa.userservice.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

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
