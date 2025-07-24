package dev.rayhan.spring_store.controllers;

import dev.rayhan.spring_store.common.PaginationHelper;
import dev.rayhan.spring_store.common.ValidationErrorHandler;
import dev.rayhan.spring_store.dtos.SortDirection;
import dev.rayhan.spring_store.dtos.UserDto;
import dev.rayhan.spring_store.dtos.UserListFilter;
import dev.rayhan.spring_store.entities.User;
import dev.rayhan.spring_store.mappers.UserMapper;
import dev.rayhan.spring_store.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
//    private final UserDtoMapper userDtoMapper;

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(
            @Valid @ModelAttribute UserListFilter filter,
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
                        .map(userMapper::toDto).toList()
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
