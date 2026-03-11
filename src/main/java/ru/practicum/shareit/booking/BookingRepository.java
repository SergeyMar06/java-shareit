package ru.practicum.shareit.booking;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.Item;

import ru.practicum.shareit.exception.InvalidFormatException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository {
    private List<Booking> bookings;
    private ItemRepository itemRepository;

    public BookingRepository(ItemRepository itemRepository) {
        this.bookings = new ArrayList<>();
        this.itemRepository = itemRepository;
    }

    public List<Booking> findAll() {
        return bookings;
    }

    public Booking findById(Long id) {
        if (id == null) {
            throw new InvalidFormatException("Передан null вместо id");
        }

        return bookings.stream()
                .filter(booking -> booking.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Booking create(Booking booking) {
        if (booking == null) {
            throw new InvalidFormatException("Передан null вместо значения Booking");
        }

        Item item = itemRepository.findById(booking.getItem().getId());

        if (item == null) {
            throw new NotFoundException("Item с ID = " + booking.getItem().getId() + " не найден");
        }

        if (!item.getAvailable()) {
            throw new InvalidFormatException("Вещь уже забронирована");
        }

        item.setAvailable(false);

        booking.setItem(item);

        booking.setId(generateId());

        bookings.add(booking);

        return booking;
    }

    public Booking update(Booking newBooking) {
        if (newBooking == null) {
            throw new InvalidFormatException("Передан null вместо объекта Booking");
        }

        if (newBooking.getId() == null) {
            throw new InvalidFormatException("Booking должен иметь id");
        }

        Booking oldBooking = findById(newBooking.getId());

        if (oldBooking == null) {
            throw new InvalidFormatException("Объекта с ID = " + newBooking.getId() + " не существует");
        }

        if (newBooking.getStart() != null) {
            oldBooking.setStart(newBooking.getStart());
        }
        if (newBooking.getEnd() != null) {
            oldBooking.setEnd(newBooking.getEnd());
        }
        if (newBooking.getItem() != null) {
            oldBooking.setItem(newBooking.getItem());
        }
        if (newBooking.getBooker() != null) {
            oldBooking.setBooker(newBooking.getBooker());
        }
        if (newBooking.getStatus() != null) {
            oldBooking.setStatus(newBooking.getStatus());
        }

        return oldBooking;
    }

    public void delete(Long id) {
        if (id == null) {
            throw new InvalidFormatException("Передан null вместо значения id");
        }

        findById(id).getItem().setAvailable(true);

        bookings.remove(findById(id));
    }

    private long generateId() {
        return bookings.stream()
                .mapToLong(Booking::getId)
                .max()
                .orElse(0) + 1;
    }
}
