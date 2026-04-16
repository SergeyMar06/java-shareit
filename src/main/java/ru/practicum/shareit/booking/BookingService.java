package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public BookingDto create(Long userId, Booking dto) {
        if (bookingRepository.findById(dto.getId()).isEmpty()) {
            throw new NotFoundException("Booking c id=" + dto.getId() + " не найден");
        }

        return BookingMapper.toDto(bookingRepository.save(dto));
    }

    public BookingDto approve(Long userId, Long bookingId, boolean approved) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow();

        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new RuntimeException("Only owner can approve");
        }

        booking.setStatus(
                approved ? BookingStatus.APPROVED : BookingStatus.REJECTED
        );

        return BookingMapper.toDto(
                bookingRepository.save(booking)
        );
    }

    public BookingDto getById(Long userId, Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow();

        boolean isOwner =
                booking.getItem().getOwner().getId().equals(userId);

        boolean isBooker =
                booking.getBooker().getId().equals(userId);

        if (!isOwner && !isBooker) {
            throw new RuntimeException("Access denied");
        }

        return BookingMapper.toDto(booking);
    }

    public List<BookingDto> getUserBookings(Long userId, BookingState state) {

        Sort sort = Sort.by("start").descending();
        LocalDateTime now = LocalDateTime.now();

        List<Booking> bookings;

        switch (state) {
            case CURRENT:
                bookings = bookingRepository.findByBooker_Id(userId, sort).stream()
                        .filter(b -> b.getStart().isBefore(now) && b.getEnd().isAfter(now))
                        .toList();
                break;

            case PAST:
                bookings = bookingRepository.findByBooker_IdAndEndIsBefore(userId, now, sort);
                break;

            case FUTURE:
                bookings = bookingRepository.findByBooker_IdAndStartIsAfter(userId, now, sort);
                break;

            case WAITING:
                bookings = bookingRepository.findByBooker_IdAndStatus(
                        userId, BookingStatus.WAITING, sort);
                break;

            case REJECTED:
                bookings = bookingRepository.findByBooker_IdAndStatus(
                        userId, BookingStatus.REJECTED, sort);
                break;

            default:
                bookings = bookingRepository.findByBooker_Id(userId, sort);
        }

        return bookings.stream()
                .map(BookingMapper::toDto)
                .toList();
    }

    public List<BookingDto> getOwnerBookings(Long userId, BookingState state) {

        Sort sort = Sort.by("start").descending();

        List<Booking> bookings;

        switch (state) {
            case WAITING:
                bookings = bookingRepository
                        .findByItem_Owner_IdAndStatus(userId, BookingStatus.WAITING, sort);
                break;

            case REJECTED:
                bookings = bookingRepository
                        .findByItem_Owner_IdAndStatus(userId, BookingStatus.REJECTED, sort);
                break;

            default:
                bookings = bookingRepository
                        .findByItem_Owner_Id(userId, sort);
        }

        return bookings.stream()
                .map(BookingMapper::toDto)
                .toList();
    }
}