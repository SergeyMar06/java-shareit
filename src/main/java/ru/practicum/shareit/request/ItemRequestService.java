package ru.practicum.shareit.request;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemRequestService {
    private ItemRequestRepository itemRequestRepository;

    public ItemRequestService(ItemRequestRepository itemRequestRepository) {
        this.itemRequestRepository = itemRequestRepository;
    }

    public List<ItemRequestDto> findAll() {
        return itemRequestRepository.findAll().stream()
                .map(ItemRequestMapper::toDto)
                .toList();
    }

    public ItemRequestDto findById(Long id) {
        return ItemRequestMapper.toDto(itemRequestRepository.findById(id));
    }

    public ItemRequestDto create(ItemRequestDto itemRequestDto) {
        return ItemRequestMapper.toDto(itemRequestRepository.create(ItemRequestMapper.fromDto(itemRequestDto)));
    }

    public ItemRequestDto update(ItemRequestDto newItemRequestDto) {
        return ItemRequestMapper.toDto(itemRequestRepository.update(ItemRequestMapper.fromDto(newItemRequestDto)));
    }

    public void delete(Long id) {
        itemRequestRepository.delete(id);
    }
}
