package ru.practicum.shareit.item;

public class ItemMapper {
    public static ItemDto toDto(Item item) { // преобразуем в dto
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setRequest(item.getRequest());

        return itemDto;
    }

    public static Item fromDto(ItemDto itemDto) { // преобразуем из dto
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setRequest(itemDto.getRequest());

        return item;
    }
}
