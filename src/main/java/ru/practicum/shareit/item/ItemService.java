package ru.practicum.shareit.item;

import java.util.List;

public interface ItemService {
    public List<ItemDto> findAll(Long userId);

    public ItemDto findById(Long itemId);

    public ItemDto create(ItemDto itemDto, Long userId);

    public ItemDto update(ItemDto itemDto, Long userId, Long itemId);

    public void delete(Long itemId);

    public List<ItemDto> searchItems(String text);
}
