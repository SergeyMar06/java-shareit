package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.InvalidFormatException;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public List<User> findAll() {
        return users;
    }

    public User findById(Long id) {
        if (id == null) {
            throw new InvalidFormatException("Передан null вместо id");
        }

        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException("Пользователь с id = " + id + " не найден")
                );
    }

    public User create(User user) {
        if (user == null) {
            throw new InvalidFormatException("Передан null вместо объекта User");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidFormatException("Email обязателен");
        }

        for (User user1 : users) {
            if (user1.getEmail().equals(user.getEmail())) {
                throw new ConflictException("Email уже используется!");
            }
        }

        user.setId(generateId());
        users.add(user);

        return user;
    }

    public User update(User newUser, Long id) {
        if (newUser == null) {
            throw new InvalidFormatException("Передан null вместо объекта User");
        }

        if (id == null) {
            throw new InvalidFormatException("User должен иметь id");
        }

        User oldUser = findById(id);

        if (oldUser == null) {
            throw new NotFoundException("Пользователя с id = " + newUser.getId() + " не найден");
        }

        if (newUser.getEmail() != null) {  // только если email передан
            for (User user1 : users) {
                if (
                        user1.getEmail().equals(newUser.getEmail()) &&
                                !user1.getId().equals(newUser.getId())
                ) {
                    throw new ConflictException("Email уже используется");
                }
            }
            oldUser.setEmail(newUser.getEmail());
        }

        if (newUser.getName() != null) {
            oldUser.setName(newUser.getName());
        }

        return oldUser;
    }

    public void delete(Long id) {
        if (id == null) {
            throw new InvalidFormatException("Передан null вместо значения id");
        }

        users.remove(findById(id));
    }

    private long generateId() {
        return users.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0) + 1;
    }
}
