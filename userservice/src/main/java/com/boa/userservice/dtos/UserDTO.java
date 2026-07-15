package com.boa.userservice.dtos;

import com.boa.userservice.models.FullName;

public record UserDTO(String id, FullNameDTO fullNameDTO, String email) {
}
