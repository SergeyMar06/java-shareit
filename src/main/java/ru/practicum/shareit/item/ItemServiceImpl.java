package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.exception.InvalidFormatException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UnauthorizedException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    public ItemServiceImpl(ItemRepository itemRepository, BookingRepository bookingRepository,
                           UserRepository userRepository, CommentRepository commentRepository) {
        this.itemRepository = itemRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<ItemDto> findAll(Long userId) {
        List<ItemDto> items = itemRepository.findAllByOwner(userId).stream()
                .map(ItemMapper::toDto)
                .toList();

        LocalDateTime now = LocalDateTime.now();

        for (ItemDto item : items) {
            List<Booking> bookings = bookingRepository.findByItem_Id(item.getId());

            Booking last = bookings.stream()
                    .filter(b -> b.getEnd().isBefore(now) && b.getStatus() == BookingStatus.APPROVED)
                    .max(Comparator.comparing(Booking::getEnd))
                    .orElse(null);

            Booking next = bookings.stream()
                    .filter(b -> b.getStart().isAfter(now) && b.getStatus() == BookingStatus.APPROVED)
                    .min(Comparator.comparing(Booking::getStart))
                    .orElse(null);

            item.setLastBooking(last != null ? BookingMapper.toDto(last) : null);
            item.setNextBooking(next != null ? BookingMapper.toDto(next) : null);

            // Добавляем комментарии сюда
            List<Comment> comments = commentRepository.findByItem_Id(item.getId());
            item.setComments(comments.stream().map(CommentMapper::toDto).toList());
        }

        return items;
    }

    @Override
    public ItemDto findById(Long itemId) {
        Item itemEntity = itemRepository.findById(itemId);

        if (itemEntity == null) {
            throw new NotFoundException("Вещь не найдена, id = " + itemId);
        }

        ItemDto item = ItemMapper.toDto(itemEntity);

        List<Comment> comments = commentRepository.findByItem_Id(itemId);
        item.setComments(comments.stream().map(CommentMapper::toDto).toList());

        return item;
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

    @Override
    public CommentDto addComment(Long itemId, Long userId, CommentDto commentDto) {
        Item item = itemRepository.findById(itemId);
        User user = userRepository.findById(userId); // убедиться, что у тебя есть UserRepository

        // Проверка: пользователь брал вещь в аренду
        boolean hasBooking = bookingRepository.findByItem_IdAndBooker_IdAndStatus(itemId, userId, BookingStatus.APPROVED)
                .stream()
                .anyMatch(b -> b.getEnd().isBefore(LocalDateTime.now()));

        if (!hasBooking) {
            throw new UnauthorizedException("Пользователь не брал эту вещь в аренду");
        }

        Comment comment = CommentMapper.fromDto(commentDto, item, user);
        return CommentMapper.toDto(commentRepository.save(comment));
    }
}
