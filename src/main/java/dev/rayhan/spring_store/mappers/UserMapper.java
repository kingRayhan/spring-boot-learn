package dev.rayhan.spring_store.mappers;


import dev.rayhan.spring_store.dtos.UserDto;
import dev.rayhan.spring_store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
