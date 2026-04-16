package ru.practicum.shareit.item;

import lombok.Data;
import ru.practicum.shareit.booking.BookingDto;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.UserDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private Long id; // id
    private String name; // название
    private String description; // описание
    private Boolean available; // статус о том, доступна или нет вещь для аренды
    private UserDto owner; // владелец вещи
    private ItemRequest request; // если вещь была создана по запросу другого пользователя, то в этом
    // поле будет храниться ссылка на соответствующий запрос
    private BookingDto lastBooking;    // последнее прошедшее бронирование
    private BookingDto nextBooking;    // ближайшее будущее бронирование
    private List<CommentDto> comments;
}
