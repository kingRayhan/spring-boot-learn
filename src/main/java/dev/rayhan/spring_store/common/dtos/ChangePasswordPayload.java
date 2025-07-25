package dev.rayhan.spring_store.common.dtos;

import lombok.Data;

@Data
public class ChangePasswordPayload {
    private String oldPassword;
    private String newPassword;
}
