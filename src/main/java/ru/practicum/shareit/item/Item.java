package ru.practicum.shareit.item;

import lombok.Data;
import ru.practicum.shareit.user.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {
    private Long id; // id
    private String name; // название
    private String description; // описание
    private Boolean available; // статус о том, доступна или нет вещь для аренды
    private User owner; // владелец вещи
    private String request; // если вещь была создана по запросу другого пользователя, то в этом
    // поле будет храниться ссылка на соответствующий запрос
}
