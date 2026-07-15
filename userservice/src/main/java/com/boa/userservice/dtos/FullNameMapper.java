package com.boa.userservice.dtos;

import com.boa.userservice.models.FullName;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FullNameMapper {

    FullNameDTO  toFullNameDTO(FullName fullName);
}
