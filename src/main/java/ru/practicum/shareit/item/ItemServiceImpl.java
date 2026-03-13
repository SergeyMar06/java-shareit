package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.InvalidFormatException;
import ru.practicum.shareit.exception.UnauthorizedException;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemDto> findAll(Long userId) {
        return itemRepository.findAllByOwner(userId).stream()
                .map(ItemMapper::toDto)
                .toList();
    }

    @Override
    public ItemDto findById(Long itemId) {
        return ItemMapper.toDto(itemRepository.findById(itemId));
    }

    @Override
    public ItemDto create(ItemDto itemDto, Long userId) {
        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            throw new InvalidFormatException("Название не может быть пустым");
        }

        if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new InvalidFormatException("Описание не может быть пустым");
        }

        if (itemDto.getAvailable() == null) {
            throw new InvalidFormatException("Поле available обязательно");
        }


        return ItemMapper.toDto(itemRepository.create(ItemMapper.fromDto(itemDto), userId));
    }

    @Override
    public ItemDto update(ItemDto itemDto, Long userId, Long itemId) {
        Item existing = itemRepository.findById(itemId);

        if (!existing.getOwner().getId().equals(userId)) {
            throw new UnauthorizedException("Вы не владелец");
        }

        Item itemToUpdate = ItemMapper.fromDto(itemDto);

        Item updated = itemRepository.update(itemToUpdate, itemId);

        return ItemMapper.toDto(updated);
    }


    @Override
    public void delete(Long itemId, Long userId) {
        if (itemId == null) {
            throw new IllegalArgumentException("Передан null вместо id");
        }

        if (!findById(itemId).getOwner().getId().equals(userId)) {
            throw new UnauthorizedException("Вы не владелец");
        }

        itemRepository.delete(itemId);
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        return itemRepository.searchItems(text).stream()
                .map(ItemMapper::toDto)
                .toList();
    }
}
