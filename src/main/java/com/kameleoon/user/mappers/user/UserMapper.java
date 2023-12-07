package com.kameleoon.user.mappers.user;

import com.kameleoon.user.dto.user.UserDto;
import com.kameleoon.user.entity.user.User;
import com.kameleoon.user.mappers.Mappers;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends Mappers<User, UserDto> {
}
