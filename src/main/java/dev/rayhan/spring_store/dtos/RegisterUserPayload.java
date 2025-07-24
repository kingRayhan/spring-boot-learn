package dev.rayhan.spring_store.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterUserPayload {
    @NotBlank(message = "Name is required")
    @Min(message = "Name must be at least 3 characters long", value = 3)
    @Max(message = "Name must be at most 255 characters long", value = 255)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Min(message = "Password must be at least 8 characters long", value = 8)
    @Max(message = "Password must be at most 255 characters long", value = 255)
    private String password;
}
