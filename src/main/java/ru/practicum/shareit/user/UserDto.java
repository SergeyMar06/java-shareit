package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserDto {
    private Long id; // id
    private String name; // имя
    @Email
    private String email; // email
}
