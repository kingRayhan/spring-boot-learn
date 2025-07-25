package dev.rayhan.spring_store.controllers;

import dev.rayhan.spring_store.common.PaginationHelper;
import dev.rayhan.spring_store.common.ValidationErrorHandler;
import dev.rayhan.spring_store.common.dtos.ChangePasswordPayload;
import dev.rayhan.spring_store.common.dtos.RegisterUserPayload;
import dev.rayhan.spring_store.common.dtos.UpdateUserRequestPayload;
import dev.rayhan.spring_store.common.dtos.UserListFilterRequestQueryParam;
import dev.rayhan.spring_store.common.entities.User;
import dev.rayhan.spring_store.common.mappers.UserMapper;
import dev.rayhan.spring_store.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "Users", description = "User related operations")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @PostMapping("/")
    public ResponseEntity<?> registerUser(
            @RequestBody RegisterUserPayload payload,
            BindingResult result,
            UriComponentsBuilder uriBuilder
    ) {
        var errors = ValidationErrorHandler.handleValidationErrors(result);
        if (errors != null) {
            return ResponseEntity.badRequest().body(errors);
        }
        var user = mapper.registerPayloadToEntity(payload);
        var createdUser = userRepository.save(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.entityToUserDto(createdUser));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(
            @Valid @ModelAttribute UserListFilterRequestQueryParam filter,
            BindingResult result
    ) {
        var errors = ValidationErrorHandler.handleValidationErrors(result);
        if (errors != null) {
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(
                userRepository
                        .findAll(
                                PaginationHelper.createPageable(
                                        filter.getPage(),
                                        filter.getLimit(),
                                        filter.getSort(),
                                        filter.getSortBy().toString()
                                )
                        )
                        .stream()
                        .map(mapper::entityToUserDto).toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUserById(
            @PathVariable UUID id,
            @RequestBody UpdateUserRequestPayload payload
    ) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        mapper.syncUpdateUserPayloadWithEntity(payload, user);

        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @PathVariable UUID id
    ) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/change-password")
    public ResponseEntity<Void> changePasswordForUserById(
            @PathVariable UUID id,
            @RequestBody ChangePasswordPayload payload
    ) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        if(!user.getPassword().equals(payload.getOldPassword())){
            return ResponseEntity.badRequest().build();
        }

        user.setPassword(payload.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }
}
