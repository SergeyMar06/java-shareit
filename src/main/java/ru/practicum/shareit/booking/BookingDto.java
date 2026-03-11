package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.user.UserDto;

import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class BookingDto {
    private Long id;
    private ItemDto item; // вещь, которую пользователь бронирует
    private UserDto booker; // пользователь, который осуществляет бронирование
    private LocalDate start; // дата и время начала бронирования
    private LocalDate end; // дата и время конца бронирования
    private BookingStatus status; //  статус бронирования
}
