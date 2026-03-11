package ru.practicum.shareit.request;

import lombok.Data;
import ru.practicum.shareit.user.UserDto;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
public class ItemRequestDto {
    private Long id;              // Уникальный идентификатор запроса
    private UserDto requestor;     //  пользователь, создавший запрос
    private String description;   // Что ищет пользователь
    private LocalDateTime created; // дата и время создания запроса
}
