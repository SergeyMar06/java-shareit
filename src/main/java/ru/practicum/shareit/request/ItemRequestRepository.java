package ru.practicum.shareit.request;

import org.springframework.stereotype.Repository;

import ru.practicum.shareit.exception.InvalidFormatException;
import ru.practicum.shareit.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRequestRepository {
    private List<ItemRequest> itemRequests;

    public ItemRequestRepository() {
        this.itemRequests = new ArrayList<>();
    }

    public List<ItemRequest> findAll() {
        return itemRequests;
    }

    public ItemRequest findById(Long id) {
        if (id == null) {
            throw new InvalidFormatException("Передан null вместо id");
        }

        return itemRequests.stream()
                .filter(itemRequest -> itemRequest.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public ItemRequest create(ItemRequest itemRequest) {
        if (itemRequest == null) {
            throw new InvalidFormatException("Передан null вместо объекта itemRequest");
        }

        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setId(generateId());

        itemRequests.add(itemRequest);

        return itemRequest;
    }

    public ItemRequest update(ItemRequest newItemRequest) {
        if (newItemRequest == null) {
            throw new InvalidFormatException("Передан null вместо объекта itemRequest");
        }

        if (newItemRequest.getId() == null) {
            throw new InvalidFormatException("ItemRequest должен иметь id");
        }

        ItemRequest oldItemRequest = findById(newItemRequest.getId());

        if (oldItemRequest == null) {
            throw new NotFoundException("ItemRequest с таким id не найден");
        }
        if (newItemRequest.getCreated() != null) {
            oldItemRequest.setCreated(newItemRequest.getCreated());
        }
        if (newItemRequest.getDescription() != null) {
            oldItemRequest.setDescription(newItemRequest.getDescription());
        }
        if (newItemRequest.getRequestor() != null) {
            oldItemRequest.setRequestor(newItemRequest.getRequestor());
        }

        return oldItemRequest;
    }

    public void delete(Long id) {
        if (id == null) {
            throw new InvalidFormatException("Передан null вместо id");
        }

        itemRequests.remove(findById(id));
    }

    private long generateId() {
        return itemRequests.stream()
                .mapToLong(ItemRequest::getId)
                .max()
                .orElse(0) + 1;
    }
}
