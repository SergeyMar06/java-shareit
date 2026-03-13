package ru.practicum.shareit.request;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private ItemRequestService itemRequestService;

    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @GetMapping
    public List<ItemRequestDto> findAll() {
        return itemRequestService.findAll();
    }

    @GetMapping("/{id}")
    public ItemRequestDto findById(@PathVariable Long id) {
        return itemRequestService.findById(id);
    }

    @PostMapping
    public ItemRequestDto create(@RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestService.create(itemRequestDto);
    }

    @PutMapping
    public ItemRequestDto update(@RequestBody ItemRequestDto newItemRequestDto) {
        return itemRequestService.update(newItemRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemRequestService.delete(id);
    }
}
