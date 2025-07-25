package dev.rayhan.spring_store.common.dtos;

import lombok.Data;

@Data
public class UpdateUserRequestPayload {
    private String name;
    private String email;
}
