package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;

import ru.practicum.shareit.exception.InvalidFormatException;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepository {
    private List<Item> itemList;
    private UserRepository userRepository;

    public ItemRepository(UserRepository userRepository) {
        this.itemList = new ArrayList<>();
        this.userRepository = userRepository;
    }

    public List<Item> findAllByOwner(Long ownerId) {
        return itemList.stream()
                .filter(item -> item.getOwner() != null)
                .filter(item -> item.getOwner().getId().equals(ownerId))
                .toList();
    }

    public Item findById(Long id) {
        if (id == null) {
            throw new InvalidFormatException("Передан null вместо id");
        }

        return itemList.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new NotFoundException("Item с ID = " + id + " не найден"));
    }

    public Item create(Item item, Long userId) {
        if (item == null) {
            throw new InvalidFormatException("Передан null вместо объекта Item");
        }

        item.setId(generateId());
        item.setAvailable(item.getAvailable());
        item.setOwner(userRepository.findById(userId));

        itemList.add(item);

        return item;
    }

    public Item update(Item item, Long itemId) {
        if (item == null) {
            throw new InvalidFormatException("Передан null вместо объекта Item");
        }
        if (itemId == null) {
            throw new InvalidFormatException("Item должен иметь id");
        }

        Item existing = findById(itemId);

        if (item.getName() != null) {
            existing.setName(item.getName());
        }
        if (item.getDescription() != null) {
            existing.setDescription(item.getDescription());
        }

        existing.setAvailable(item.getAvailable());

        return existing;
    }


    public void delete(Long itemId) {
        itemList.remove(findById(itemId));
    }

    public List<Item> searchItems(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }

        String lowerText = text.toLowerCase();

        return itemList.stream()
                .filter(item -> item != null) // проверяем, что item не null
                .filter(item -> item.getAvailable() != null && item.getAvailable()) // доступные и не null
                .filter(item -> {
                    String name = item.getName() != null ? item.getName().toLowerCase() : "";
                    String description = item.getDescription() != null ? item.getDescription().toLowerCase() : "";
                    return name.contains(lowerText) || description.contains(lowerText);
                })
                .toList();
    }



    private long generateId() {
        return itemList.stream()
                .mapToLong(Item::getId)
                .max()
                .orElse(0) + 1;
    }
}
