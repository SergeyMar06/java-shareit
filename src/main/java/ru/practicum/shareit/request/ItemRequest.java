package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@Entity
@Table(name = "requests")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;              // Уникальный идентификатор запроса
    @ManyToOne
    @JoinColumn(name = "requestor_id")
    private User requestor;     //  пользователь, создавший запрос
    @Column(name = "description")
    private String description;   // Что ищет пользователь
    @Column(name = "created")
    private LocalDateTime created; // дата и время создания запроса
}
