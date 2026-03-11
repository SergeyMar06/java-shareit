package ru.practicum.shareit.request;

import ru.practicum.shareit.user.UserMapper;

public class ItemRequestMapper {
    public static ItemRequestDto toDto(ItemRequest itemRequest) { // преобразуем в dto
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setCreated(itemRequest.getCreated());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setRequestor(UserMapper.toDto(itemRequest.getRequestor()));

        return itemRequestDto;
    }

    public static ItemRequest fromDto(ItemRequestDto itemRequestDto) { // преобразуем из dto
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(itemRequestDto.getId());
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setCreated(itemRequestDto.getCreated());
        itemRequest.setRequestor(UserMapper.fromDto(itemRequestDto.getRequestor()));

        return itemRequest;
    }
}
