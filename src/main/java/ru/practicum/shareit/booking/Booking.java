package ru.practicum.shareit.booking;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item; // вещь, которую пользователь бронирует
    @ManyToOne
    @JoinColumn(name = "booker_id")
    private User booker; // пользователь, который осуществляет бронирование
    @Column(name = "start_date")
    private LocalDateTime start; // дата и время начала бронирования
    @Column(name = "end_date")
    private LocalDateTime end; // дата и время конца бронирования
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status; //  статус бронирования
}
