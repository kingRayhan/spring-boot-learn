package dev.rayhan.spring_store.controllers;

import dev.rayhan.spring_store.common.PaginationHelper;
import dev.rayhan.spring_store.common.ValidationErrorHandler;
import dev.rayhan.spring_store.dtos.RegisterUserPayload;
import dev.rayhan.spring_store.dtos.UserListFilterRequestQueryParam;
import dev.rayhan.spring_store.entities.User;
import dev.rayhan.spring_store.mappers.UserMapper;
import dev.rayhan.spring_store.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
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
        var user = mapper.toEntity(payload);
        var createdUser = userRepository.save(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toDto(createdUser));
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
                        .map(mapper::toDto).toList()
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


}
