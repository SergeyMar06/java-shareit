package ru.practicum.shareit.item;

import ru.practicum.shareit.comment.CommentDto;

import java.util.List;

public interface ItemService {
    public List<ItemDto> findAll(Long userId);

    public ItemDto findById(Long itemId);

    public ItemDto create(ItemDto itemDto, Long userId);

    public ItemDto update(ItemDto itemDto, Long userId, Long itemId);

    public void delete(Long itemId, Long userId);

    public List<ItemDto> searchItems(String text);

    public CommentDto addComment(Long itemId, Long userId, CommentDto commentDto);
}
