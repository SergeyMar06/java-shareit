package ru.practicum.shareit.item;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // id
    @Column(name = "name")
    private String name; // название
    @Column(name = "description")
    private String description; // описание
    @Column(name = "is_available")
    private Boolean available; // статус о том, доступна или нет вещь для аренды
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner; // владелец вещи
    @ManyToOne
    @JoinColumn(name = "request_id")
    private ItemRequest request; // если вещь была создана по запросу другого пользователя, то в этом
    // поле будет храниться ссылка на соответствующий запрос
}
