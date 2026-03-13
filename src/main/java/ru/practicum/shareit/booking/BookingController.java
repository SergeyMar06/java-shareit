package ru.practicum.shareit.booking;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingDto> findAll() {
        return bookingService.findAll();
    }

    @GetMapping("/{id}")
    public BookingDto findById(@PathVariable Long id) {
        return bookingService.findById(id);
    }

    @PostMapping
    public BookingDto create(@RequestBody BookingDto bookingDto) {
        return bookingService.create(bookingDto);
    }

    @PutMapping
    public BookingDto update(@RequestBody BookingDto newBookingDto) {
        return bookingService.update(newBookingDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.delete(id);
    }
}
