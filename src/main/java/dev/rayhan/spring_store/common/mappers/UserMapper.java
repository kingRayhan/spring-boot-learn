package dev.rayhan.spring_store.common.mappers;


import dev.rayhan.spring_store.common.dtos.RegisterUserPayload;
import dev.rayhan.spring_store.common.dtos.UpdateUserRequestPayload;
import dev.rayhan.spring_store.common.dtos.UserDto;
import dev.rayhan.spring_store.common.entities.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto entityToUserDto(User user);
    User registerPayloadToEntity(RegisterUserPayload registerUserPayload);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void syncUpdateUserPayloadWithEntity(UpdateUserRequestPayload userDto, @MappingTarget User user);
}
